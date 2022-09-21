package com.metalbono.blogsearchapi.search.service;

import com.metalbono.blogsearchapi.search.model.BlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import com.metalbono.blogsearchapi.search.model.request.BlogSearchRequest;

public interface BlogSearchService<T> {

    BlogSearchSource getSource();

    default BlogSearchResponse searchBlog(BlogSearchRequest request) {
        return convertToBlogSearchResponse(search(request));
    }

    T search(BlogSearchRequest request);

    BlogSearchResponse convertToBlogSearchResponse(T response);

}
