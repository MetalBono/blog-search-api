package com.metalbono.blogsearchapi.search.client.naver;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class NaverBlogSearchApiConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", "yk5VY_isAAyiAX0mq2FG");
            requestTemplate.header("X-Naver-Client-Secret", "xnth4qWb9f");
        };
    }
}