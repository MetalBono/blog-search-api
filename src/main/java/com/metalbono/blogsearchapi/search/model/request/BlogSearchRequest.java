package com.metalbono.blogsearchapi.search.model.request;

import lombok.Builder;
import lombok.Getter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
@Builder
public class BlogSearchRequest {
    private String query;
    private String sort;
    private String source;
    private int page;
    private int size;
}
