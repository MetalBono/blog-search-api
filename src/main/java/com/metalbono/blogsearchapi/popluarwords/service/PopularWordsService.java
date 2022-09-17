package com.metalbono.blogsearchapi.popluarwords.service;

import com.metalbono.blogsearchapi.popluarwords.adapter.outbound.port.PopularWordsPort;
import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularWordsService {

    private final String POPULAR_WORDS_TOP_10_CACHE_KEY = "POPULAR_WORDS_TOP_10";

    private final PopularWordsPort popularWordsPort;

    @Cacheable(value = POPULAR_WORDS_TOP_10_CACHE_KEY)
    public List<KeywordSearchStatistics> getPopularWordsTop10() {
        return popularWordsPort.getPopularWordsTop10();
    }

    @CacheEvict(value = POPULAR_WORDS_TOP_10_CACHE_KEY)
    public void clearPopularWordsTop10Cache() {
    }
}
