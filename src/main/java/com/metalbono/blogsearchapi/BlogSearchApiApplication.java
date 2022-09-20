package com.metalbono.blogsearchapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableCaching
@EnableScheduling
@EnableFeignClients
@EnableTransactionManagement
@SpringBootApplication
public class BlogSearchApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSearchApiApplication.class, args);
    }
}
