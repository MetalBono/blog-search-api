package com.metalbono.blogsearchapi.statistics.adapter.outbound;

import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import com.metalbono.blogsearchapi.statistics.repository.KeywordStatisticsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class KeywordStatisticsPersistenceAdapterTest {

    @Mock
    private KeywordStatisticsRepository keywordStatisticsRepository;
    @InjectMocks
    private KeywordStatisticsPersistenceAdapter keywordStatisticsPersistenceAdapter;

    @Test
    @DisplayName("검색어 통계 업데이트")
    public void testUpdateKeywordStatistics() {
        // given
        List<KeywordSearchStatistics> entities = mock(List.class);
        int expected = entities.size();

        // when
        int actual = keywordStatisticsPersistenceAdapter.updateKeywordStatistics(entities);

        // then
        assertEquals(expected, actual);
        verify(keywordStatisticsRepository, times(1)).saveAll(entities);
    }

    @Test
    @DisplayName("검색어 통계 조회")
    public void testGetKeywordStatistics() {
        // given
        List<String> keywords = mock(List.class);
        List<KeywordSearchStatistics> expected = mock(List.class);
        when(keywordStatisticsRepository.findByKeywordIn(keywords)).thenReturn(expected);

        // when
        List<KeywordSearchStatistics> actual = keywordStatisticsPersistenceAdapter.getKeywordStatistics(keywords);

        // then
        assertEquals(expected, actual);
        verify(keywordStatisticsRepository, times(1)).findByKeywordIn(keywords);
    }
}
