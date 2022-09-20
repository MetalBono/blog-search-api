package com.metalbono.blogsearchapi.search.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class BlogContent {
    private String title;
    private String contents;
    private String blogUrl;
    private String blogName;
    private String postDate;
    private Map<String, Object> metadata;
}
