package com.metalbono.blogsearchapi.popluarwords.adapter.outbound;

import com.metalbono.blogsearchapi.popluarwords.adapter.outbound.port.PopularWordsPort;
import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import com.metalbono.blogsearchapi.statistics.repository.KeywordStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PopularWordsPersistenceAdapter implements PopularWordsPort {
    private final KeywordStatisticsRepository keywordStatisticsRepository;

    @Override
    public List<KeywordSearchStatistics> getPopularWordsTop10() {
        return keywordStatisticsRepository.findTop10ByOrderByCountDesc();
    }
}
