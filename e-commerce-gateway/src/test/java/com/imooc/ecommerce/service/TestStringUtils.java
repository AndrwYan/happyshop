package com.imooc.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestStringUtils {

    @Test
    public void TestM(){
        String a = "/imooc/e-commerce/login";
        assertEquals(checkWhiteListUrl(a),true);
    }
    /**
     * <h2>校验是否是白名单接口</h2>
     * */
    private boolean checkWhiteListUrl(String url) {
        List<String> list = Arrays.asList("/login", "/register", "/catchall");
        CharSequence[] cs = list.toArray(new CharSequence[list.size()]);
        return StringUtils.containsAny(
                url, cs
        );

    }
}
