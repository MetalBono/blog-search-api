package com.metalbono.blogsearchapi.search.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
public class BlogSearchKeywordProducer {

    private final BlockingQueue<String> searchKeywordQueue;

    @Async
    public void processAsync(String query) {
        if (query != null) {
            for (String value : query.split(" ")) {
                if (!isHttpUrl(value)) {
                    searchKeywordQueue.add(value);
                }
            }
        }
    }

    private boolean isHttpUrl(String value) {
        return value != null && (value.startsWith("http://") || value.startsWith("https://"));
    }
}
