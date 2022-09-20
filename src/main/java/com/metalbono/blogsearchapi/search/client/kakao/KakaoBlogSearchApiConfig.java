package com.metalbono.blogsearchapi.search.client.kakao;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class KakaoBlogSearchApiConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "KakaoAK 645182949917f59955a6134d5aa92c0c");
        };
    }
}
