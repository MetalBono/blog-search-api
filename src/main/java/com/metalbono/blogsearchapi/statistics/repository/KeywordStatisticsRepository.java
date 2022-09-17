package com.metalbono.blogsearchapi.statistics.repository;

import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordStatisticsRepository extends JpaRepository<KeywordSearchStatistics, String> {
    List<KeywordSearchStatistics> findTop10ByOrderByCountDesc();
}
