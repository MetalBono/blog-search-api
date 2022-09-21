package com.metalbono.blogsearchapi.search.consumer;

import com.metalbono.blogsearchapi.statistics.service.KeywordStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class BlogSearchKeywordConsumer {

    @Value("${blog-search.search-keyword.consumer.buffer-size:1000}")
    private int bufferSize;
    private ExecutorService executor;

    private final BlockingQueue<String> searchKeywordQueue;
    private final KeywordStatisticsService keywordStatisticsService;

    private List<String> keywordBuffer = new ArrayList<>();

    protected void flushBuffer() {
        if (!CollectionUtils.isEmpty(keywordBuffer)) {
            List<String> copied;
            synchronized (keywordBuffer) {
                copied = new ArrayList<>(keywordBuffer);
                this.keywordBuffer.clear();
            }

            keywordStatisticsService.updateKeywordStatistics(copied);
        }
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void schedule() {
        flushBuffer();
    }

    @PostConstruct
    public void postConstruct() {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true) {
                keywordBuffer.add(searchKeywordQueue.take());
                if (keywordBuffer.size() >= bufferSize) {
                    flushBuffer();
                }
            }
        });
    }

    @PreDestroy
    public void preDestroy() {
        executor.shutdown();
    }
}
