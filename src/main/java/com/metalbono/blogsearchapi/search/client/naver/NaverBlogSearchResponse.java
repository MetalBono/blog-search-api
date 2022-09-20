package com.metalbono.blogsearchapi.search.client.naver;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverBlogSearchResponse {
    private Integer total;
    private Integer start;
    private Integer display;
    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        private String title;
        private String description;
        private String bloggerlink;
        private String bloggername;
        private String postdate;
        private String link;
    }
}