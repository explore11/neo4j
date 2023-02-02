package com.hr.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableNeo4jRepositories
@ComponentScan(basePackages = "com.hr")
public class Neo4jApplication {
    public static void main(String[] args) {
        SpringApplication.run(Neo4jApplication.class, args);
    }
}
