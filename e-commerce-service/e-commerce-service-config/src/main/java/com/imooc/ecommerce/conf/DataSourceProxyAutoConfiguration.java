package com.imooc.ecommerce.conf;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

/**
 * @Description: Seata所需要的数据源代理配置类。需要用到Seata的的需要依这个模块。
 * @Author: yfk
 * @Date: 2022-07-07
 * @return: null
 **/
@Configuration
public class DataSourceProxyAutoConfiguration {

    //构造函数注入
    private final DataSourceProperties dataSourceProperties;
    public DataSourceProxyAutoConfiguration(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    /**
     * <h2>
     *      配置数据源代理, 用于 Seata 全局事务回滚
     * </h2>
     *  ? :为什么需要配置数据源代理？----》before image + after image -> undo_log
     * */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        //配置文件中的数据库配置源信息
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());

        return new DataSourceProxy(dataSource);
    }
}
