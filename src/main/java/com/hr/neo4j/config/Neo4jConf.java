package com.hr.neo4j.config;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * neo4j初始化配置
 */
@Configuration
public class Neo4jConf {
    @Value("${spring.data.neo4j.uri}")
    private String url;
    @Value("${spring.data.neo4j.username}")
    private String username;
    @Value("${spring.data.neo4j.password}")
    private String password;

    @Bean(name = "driver")
    public Driver initDriver() {
        Driver driver;
        try {
            //GraphDatabase类有一个叫做静态方法driver()接受一个连接Neo4j的URL和AuthToken。
            //可以使用默认用户名和密码“neo4j” 创建基本AuthToken
            driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return driver;
    }
}
