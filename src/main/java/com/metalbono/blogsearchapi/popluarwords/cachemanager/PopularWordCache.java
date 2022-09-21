package com.metalbono.blogsearchapi.popluarwords.cachemanager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@Getter
@RequiredArgsConstructor
public enum PopularWordCache {

    POPULAR_WORDS_TOP10(Constants.POPULAR_WORDS_TOP10_VALUE, 10, TimeUnit.SECONDS, 100);

    private final String cacheName;
    private final int expireTime;
    private final TimeUnit expireTimeUnit;
    private final int maximumSize;

    public static class Constants {
        public static final String POPULAR_WORDS_TOP10_VALUE = "popularWordsTop10";
    }
}
