package com.metalbono.blogsearchapi.search.model.request;

import lombok.Builder;
import lombok.Getter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
@Builder
public class BlogSearchRequest {
    private String query;       // 검색어
    private String sort;        // 정렬 기준
    private String source;      // 검색 소스 (Kakao, Naver, ...)
    private int page;           // 페이지 번호
    private int size;           // 페이지 크기
}
