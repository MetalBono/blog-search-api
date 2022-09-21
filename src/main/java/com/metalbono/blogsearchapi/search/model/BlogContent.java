package com.metalbono.blogsearchapi.search.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class BlogContent {
    private String title;                   // 블로그 문서 제목
    private String contents;                // 블로그 문서 내용
    private String blogUrl;                 // 블로그 주소 URL
    private String blogName;                // 블로그 이름
    private String postDate;                // 포스팅 날짜 (yyyy-MM-dd)
    private Map<String, Object> metadata;   // 추가 정보
}
