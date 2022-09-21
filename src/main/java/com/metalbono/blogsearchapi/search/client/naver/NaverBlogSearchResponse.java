package com.metalbono.blogsearchapi.search.client.naver;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Naver Open API 블로그 검색 결과 모델
 */
@Getter
@Setter
public class NaverBlogSearchResponse {
    private Integer total;          // 검색어에 해당하는 총 문서 수
    private Integer start;          // 페이지 번호
    private Integer display;        // 페이지 크기
    private List<Item> items;       // 블로그 문서 목록

    @Getter
    @Setter
    public static class Item {
        private String title;           // 블로그 문서 제목
        private String description;     // 블로그 문서 내용
        private String bloggerlink;     // 블로그 주소
        private String bloggername;     // 블로그 이름
        private String postdate;        // 블로그 글 작성 시간 (yyyyMMdd)
        private String link;            // 블로그 문서 접근 URL
    }
}