package com.metalbono.blogsearchapi.statistics.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "keyword_search_statistics", schema = "blogsearch")
public class KeywordSearchStatistics {
    @Id
    @Column(name = "keyword")
    private String keyword;     // 검색어
    @Column(name = "count")
    private Long count;         // 검색된 횟수
}
