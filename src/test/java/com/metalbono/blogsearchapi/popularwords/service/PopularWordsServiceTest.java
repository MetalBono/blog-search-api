package com.metalbono.blogsearchapi.popularwords.service;

import com.metalbono.blogsearchapi.popluarwords.adapter.outbound.port.PopularWordsPort;
import com.metalbono.blogsearchapi.popluarwords.service.PopularWordsService;
import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PopularWordsServiceTest {

    @Mock
    private PopularWordsPort popularWordsPort;

    @InjectMocks
    private PopularWordsService popularWordsService;

    @Test
    @DisplayName("인기 검색어 목록 조회")
    public void testGetPopularWordsTop10() {
        // given
        List<KeywordSearchStatistics> expected = new ArrayList<>();
        expected.add(new KeywordSearchStatistics());
        expected.add(new KeywordSearchStatistics());
        expected.add(new KeywordSearchStatistics());
        when(popularWordsPort.getPopularWordsTop10()).thenReturn(expected);

        // when
        List<KeywordSearchStatistics> actual = popularWordsService.getPopularWordsTop10();

        // then
        assertEquals(actual, expected);
        verify(popularWordsPort, times(1)).getPopularWordsTop10();
    }
}
