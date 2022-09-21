package com.metalbono.blogsearchapi.popularwords.adapter.outbound;

import com.metalbono.blogsearchapi.popluarwords.adapter.outbound.PopularWordsPersistenceAdapter;
import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import com.metalbono.blogsearchapi.statistics.repository.KeywordStatisticsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PopularWordsPersistenceAdapterTest {

    @Mock
    private KeywordStatisticsRepository keywordStatisticsRepository;

    @InjectMocks
    private PopularWordsPersistenceAdapter popularWordsPersistenceAdapter;

    @Test
    @DisplayName("인기 검색어 TOP 10 조회 (영속성 레이어)")
    public void testGetPopularWordsTop10() {
        // given
        List<KeywordSearchStatistics> expected = new ArrayList<>();
        expected.add(new KeywordSearchStatistics());
        expected.add(new KeywordSearchStatistics());
        when(keywordStatisticsRepository.findTop10ByOrderByCountDesc()).thenReturn(expected);

        // when
        List<KeywordSearchStatistics> actual = popularWordsPersistenceAdapter.getPopularWordsTop10();
        assertEquals(actual, expected);
        verify(keywordStatisticsRepository, times(1)).findTop10ByOrderByCountDesc();
    }
}
