package com.metalbono.blogsearchapi.popluarwords.adapter.outbound.port;

import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;

import java.util.List;

public interface PopularWordsPort {
    List<KeywordSearchStatistics> getPopularWordsTop10();
}
