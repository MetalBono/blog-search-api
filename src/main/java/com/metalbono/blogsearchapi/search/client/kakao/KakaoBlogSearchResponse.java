package com.metalbono.blogsearchapi.search.client.kakao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoBlogSearchResponse {

    private Meta meta;
    private List<Document> documents;

    @Getter
    @Setter
    public static class Meta {
        private Integer total_count;
        private Integer pageable_count;
        private Boolean is_end;
    }

    @Getter
    @Setter
    public static class Document {
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String datetime;
        private String thumbnail;
    }
}
