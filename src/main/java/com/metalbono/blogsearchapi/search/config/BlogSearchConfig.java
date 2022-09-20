package com.metalbono.blogsearchapi.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Configuration
public class BlogSearchConfig {

    @Value("${blog-search.search-keyword.queue-size:1000}")
    private int searchKeywordQueueSize;

    @Bean
    public BlockingQueue<String> searchKeywordQueue() {
        return new ArrayBlockingQueue(searchKeywordQueueSize);
    }
}
