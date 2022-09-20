package com.metalbono.blogsearchapi.statistics.service;

import com.metalbono.blogsearchapi.statistics.adapter.outbound.port.GetKeywordStatisticsPort;
import com.metalbono.blogsearchapi.statistics.adapter.outbound.port.UpdateKeywordStatisticsPort;
import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordStatisticsService {

    private final GetKeywordStatisticsPort getKeywordStatisticsPort;
    private final UpdateKeywordStatisticsPort updateKeywordStatisticsPort;

    @Transactional
    public void updateKeywordStatistics(List<String> keywords) {
        if (CollectionUtils.isEmpty(keywords)) {
            return;
        }

        Map<String, Long> aggregatedMap = aggregateKeywords(keywords);
        Map<String, KeywordSearchStatistics> keywordSearchStatisticsMap = getKeywordStatisticsFromPersistence(keywords);

        // 영속성 레이어 업데이트
        for (KeywordSearchStatistics statistics : keywordSearchStatisticsMap.values()) {
            statistics.setCount(statistics.getCount() + aggregatedMap.get(statistics.getKeyword()));
        }

        // 신규 추가
        List<KeywordSearchStatistics> toBeInserted = getToBeInserted(aggregatedMap, keywordSearchStatisticsMap.keySet());
        updateKeywordStatisticsPort.updateKeywordStatistics(toBeInserted);
    }

    private Map<String, Long> aggregateKeywords(List<String> keywords) {
        if (CollectionUtils.isEmpty(keywords)) {
            return Collections.emptyMap();
        }
        return keywords.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }

    private Map<String, KeywordSearchStatistics> getKeywordStatisticsFromPersistence(List<String> keywords) {
        return getKeywordStatisticsPort.getKeywordStatistics(keywords)
                .stream()
                .collect(Collectors.toMap(
                        KeywordSearchStatistics::getKeyword,
                        Function.identity(),
                        (k1, k2) -> k1
                ));
    }

    private List<KeywordSearchStatistics> getToBeInserted(Map<String, Long> aggregatedMap, Set<String> existsKeywordSet) {
        if (CollectionUtils.isEmpty(aggregatedMap)) {
            return Collections.emptyList();
        }

        return aggregatedMap.entrySet().stream()
                .filter(entry -> existsKeywordSet == null ? true : !existsKeywordSet.contains(entry.getKey()))
                .map(entry -> {
                    KeywordSearchStatistics k = new KeywordSearchStatistics();
                    k.setKeyword(entry.getKey());
                    k.setCount(entry.getValue());
                    return k;
                })
                .collect(Collectors.toList());
    }
}
