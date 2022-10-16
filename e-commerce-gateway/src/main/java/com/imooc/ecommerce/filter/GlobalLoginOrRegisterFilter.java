package com.imooc.ecommerce.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.ecommerce.conf.IgnoreWhiteProperties;
import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.util.TokenParseUtil;
import com.imooc.ecommerce.vo.CommonResponse;
import com.imooc.ecommerce.vo.LoginUserInfo;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <h1>全局登录鉴权过滤器</h1>
 * */
@Slf4j
@Component
public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {

    private final IgnoreWhiteProperties ignoreWhite;

    /** 注册中心客户端, 可以从注册中心中获取服务实例信息 */
    private final LoadBalancerClient loadBalancerClient;
    private final RestTemplate restTemplate;

    public GlobalLoginOrRegisterFilter(IgnoreWhiteProperties ignoreWhite, LoadBalancerClient loadBalancerClient,
                                       RestTemplate restTemplate) {
        this.ignoreWhite = ignoreWhite;
        this.loadBalancerClient = loadBalancerClient;
        this.restTemplate = restTemplate;
    }

    /**
     * <h2>登录、注册、鉴权</h2>
     * 1. 如果是登录或注册, 则去授权中心拿到 Token 并返回给客户端
     * 2. 如果是访问其他的服务, 则鉴权, 没有权限返回 401
     * */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 跳过不需要验证的路径
        if (checkWhiteListUrl(request.getURI().getPath())) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;

        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        } catch (Exception ex) {
            log.error("parse user info from token error: [{}]", ex.getMessage(), ex);
        }

        // 获取不到登录用户信息, 返回 401
        if (null == loginUserInfo) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 解析通过, 则放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }

    /**
     * <h2>从授权中心获取 Token</h2>
     * */
    private CommonResponse getTokenFromAuthorityCenter(ServerHttpRequest request, String uriFormat) {

        // service id 就是服务名字, 负载均衡
        ServiceInstance serviceInstance = loadBalancerClient.choose(
                CommonConstant.AUTHORITY_CENTER_SERVICE_ID
        );

        log.info("Nacos Client Info: [{}], [{}], [{}]",
                serviceInstance.getServiceId(), serviceInstance.getInstanceId(),
                JSON.toJSONString(serviceInstance.getMetadata()));

        //获取服务的地址和端口号
        String requestUrl = String.format(
                uriFormat, serviceInstance.getHost(), serviceInstance.getPort()
        );

        //反序列化
        UsernameAndPassword requestBody = JSON.parseObject(
                parseBodyFromRequest(request), UsernameAndPassword.class
        );

        log.info("login request url and body: [{}], [{}]", requestUrl,
                JSON.toJSONString(requestBody));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //发送http请求到---鉴权中心服务
        CommonResponse token = restTemplate.postForObject(
                requestUrl,     //鉴权中心的路径地址
                new HttpEntity<>(JSON.toJSONString(requestBody), headers),
                CommonResponse.class
        );

        return token!=null?token:null;
    }

    /**
     * <h2>从 Post 请求中获取到请求数据</h2>
     * */
    private String parseBodyFromRequest(ServerHttpRequest request) {

        // 获取请求体
        Flux<DataBuffer> body = request.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();

        // 订阅缓冲区去消费请求体中的数据
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            // 一定要使用 DataBufferUtils.release 释放掉, 否则, 会出现内存泄露(所谓内存泄露就是一些对象无法被垃圾收集器回收)
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });

        // 获取 request body
        return bodyRef.get();
    }
    /**
     * 返回response
     *
     * @param exchange
     * @param message  异常信息
     * @param status   data中的status
     * @return
     */
    public static Mono<Void> errorInfo(ServerWebExchange exchange, String message, Integer status) {
        // 自定义返回格式
        Map<String, Object> resultMap = new HashMap<>(8);
        resultMap.put("status", status == null ? 2 : status);
        resultMap.put("message", StringUtils.isBlank(message) ? "服务异常！" : message);
        resultMap.put("info", null);
        resultMap.put("data", null);
        return getVoidMono(exchange, resultMap);
    }

    public static Mono<Void> wrapperData(ServerWebExchange exchange,Object object) {

        return getVoidMono(exchange, object);
    }

    private static Mono<Void> getVoidMono(ServerWebExchange exchange, Object object) {
        return Mono.defer(() -> {
            byte[] bytes;
            try {
                bytes = new ObjectMapper().writeValueAsBytes(object);
            } catch (JsonProcessingException e) {
                log.error("网关响应异常：", e);
                throw new RuntimeException("信息序列化异常");
            } catch (Exception e) {
                log.error("网关响应异常：", e);
                throw new RuntimeException("写入响应异常");
            }

            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());

            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Flux.just(buffer));
        });
    }

    /**
     * <h2>校验是否是白名单接口</h2>
     * */
    private boolean checkWhiteListUrl(String url) {

        List<String> list = ignoreWhite.getWhites();

        CharSequence[] cs = list.toArray(new CharSequence[list.size()]);
        return StringUtils.containsAny(
                url, cs
        );

    }

}
