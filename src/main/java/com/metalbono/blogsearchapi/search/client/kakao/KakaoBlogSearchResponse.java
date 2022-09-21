package com.metalbono.blogsearchapi.search.client.kakao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Kakao Open API 블로그 검색 결과 모델
 */
@Getter
@Setter
public class KakaoBlogSearchResponse {

    private Meta meta;                  // 메타 정보
    private List<Document> documents;   // 블로그 문서 목록

    @Getter
    @Setter
    public static class Meta {
        private Integer total_count;        // 검색어에 해당하는 총 문서 수
        private Boolean is_end;             // 마지막 페이지 여부
    }

    @Getter
    @Setter
    public static class Document {
        private String title;               // 블로그 문서 제목
        private String contents;            // 블로그 문서 내용
        private String url;                 // 블로그 문서 접근 URL
        private String blogname;            // 블로그 이름
        private String datetime;            // 블로그 글 작성 시간 (ISO 8601, [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz])
        private String thumbnail;           // 검색 시스템에서 추출한 대표 미리보기 이미지 URL
    }
}
