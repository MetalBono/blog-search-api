package com.metalbono.blogsearchapi.search.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BlogSearchResponse {
    private int totalCount;                     // 총 문서 개수
    private boolean hasNext;                    // 다음 페이지 존재 여부
    private BlogSearchSource source;            // 검색 소스 (Kakao, Naver, ...)
    private List<BlogContent> blogContents;     // 검색된 블로그 컨텐츠 목록
}
