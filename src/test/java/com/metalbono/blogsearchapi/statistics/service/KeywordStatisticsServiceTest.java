package com.metalbono.blogsearchapi.statistics.service;

import com.metalbono.blogsearchapi.statistics.adapter.outbound.port.GetKeywordStatisticsPort;
import com.metalbono.blogsearchapi.statistics.adapter.outbound.port.UpdateKeywordStatisticsPort;
import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KeywordStatisticsServiceTest {

    @Mock
    private GetKeywordStatisticsPort getKeywordStatisticsPort;
    @Mock
    private UpdateKeywordStatisticsPort updateKeywordStatisticsPort;
    @InjectMocks
    private KeywordStatisticsService keywordStatisticsService;

    @Test
    @DisplayName("검색어 통계 생성 테스트")
    public void testUpdateKeywordStatistics() {
        // given
        List<String> keywords = new ArrayList<>();
        keywords.add(UUID.randomUUID().toString());
        keywords.add(UUID.randomUUID().toString());
        keywords.add(UUID.randomUUID().toString());
        keywords.add(UUID.randomUUID().toString());
        keywords.add(UUID.randomUUID().toString());

        List<KeywordSearchStatistics> statistics = new ArrayList<>();
        for (int i = 0; i < keywords.size() - 2; i++) {
            KeywordSearchStatistics k = new KeywordSearchStatistics();
            k.setCount(1L);
            k.setKeyword(keywords.get(i));
            statistics.add(k);
        }
        when(getKeywordStatisticsPort.getKeywordStatistics(keywords)).thenReturn(statistics);

        // when
        keywordStatisticsService.updateKeywordStatistics(keywords);

        // then
        verify(getKeywordStatisticsPort, times(1)).getKeywordStatistics(keywords);
        verify(updateKeywordStatisticsPort, times(1)).updateKeywordStatistics(anyList());
    }
}
