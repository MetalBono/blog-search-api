package com.metalbono.blogsearchapi.popluarwords.cachemanager;

import com.metalbono.blogsearchapi.popluarwords.service.PopularWordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopularWordsCacheManager {

    private final PopularWordsService popularWordsService;

    @Scheduled(cron = "*/10 * * * * *")
    public void clearCache() {
        popularWordsService.clearPopularWordsTop10Cache();
    }
}
