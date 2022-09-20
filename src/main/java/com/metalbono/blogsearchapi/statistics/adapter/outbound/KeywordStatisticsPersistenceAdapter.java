package com.metalbono.blogsearchapi.statistics.adapter.outbound;

import com.metalbono.blogsearchapi.statistics.adapter.outbound.port.GetKeywordStatisticsPort;
import com.metalbono.blogsearchapi.statistics.adapter.outbound.port.UpdateKeywordStatisticsPort;
import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import com.metalbono.blogsearchapi.statistics.repository.KeywordStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KeywordStatisticsPersistenceAdapter implements UpdateKeywordStatisticsPort, GetKeywordStatisticsPort {

    private final KeywordStatisticsRepository keywordStatisticsRepository;

    @Override
    public int updateKeywordStatistics(List<KeywordSearchStatistics> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return 0;
        }
        return keywordStatisticsRepository.saveAll(entities).size();
    }

    @Override
    public List<KeywordSearchStatistics> getKeywordStatistics(List<String> keywords) {
        if (CollectionUtils.isEmpty(keywords)) {
            return Collections.emptyList();
        }
        return keywordStatisticsRepository.findByKeywordIn(keywords);
    }
}
