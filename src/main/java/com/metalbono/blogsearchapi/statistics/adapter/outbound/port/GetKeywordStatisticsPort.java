package com.metalbono.blogsearchapi.statistics.adapter.outbound.port;

import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;

import java.util.List;

public interface GetKeywordStatisticsPort {
    List<KeywordSearchStatistics> getKeywordStatistics(List<String> keywords);
}
