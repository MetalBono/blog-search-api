package com.metalbono.blogsearchapi.search.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BlogSearchResponse {
    private int totalCount;
    private boolean hasNext;
    private BlogSearchSource source;
    private List<BlogContent> blogContents;
}
