package com.metalbono.blogsearchapi.popluarwords.service;

import com.metalbono.blogsearchapi.popluarwords.adapter.outbound.port.PopularWordsPort;
import com.metalbono.blogsearchapi.popluarwords.cachemanager.PopularWordCache;
import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularWordsService {

    private final PopularWordsPort popularWordsPort;

    @Cacheable(cacheNames = { PopularWordCache.Constants.POPULAR_WORDS_TOP10_VALUE })
    public List<KeywordSearchStatistics> getPopularWordsTop10() {
        return popularWordsPort.getPopularWordsTop10();
    }
}
