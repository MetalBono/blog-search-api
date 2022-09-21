package com.metalbono.blogsearchapi.statistics.repository;

import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface KeywordStatisticsRepository extends JpaRepository<KeywordSearchStatistics, String> {
    List<KeywordSearchStatistics> findTop10ByOrderByCountDesc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<KeywordSearchStatistics> findByKeywordIn(Iterable<String> keyword);
}
