package com.metalbono.blogsearchapi.popularwords.cachemanager;

import com.metalbono.blogsearchapi.popluarwords.cachemanager.PopularWordsCacheManager;
import com.metalbono.blogsearchapi.popluarwords.service.PopularWordsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PopularWordsCacheManagerTest {

    @Mock
    private PopularWordsService popularWordsService;
    @InjectMocks
    private PopularWordsCacheManager popularWordsCacheManager;

    @Test
    @DisplayName("인기 검색어 목록 캐시 초기화")
    public void testClearCache() {
        // when
        popularWordsCacheManager.clearCache();

        // then
        verify(popularWordsService, times(1)).clearPopularWordsTop10Cache();
    }
}
