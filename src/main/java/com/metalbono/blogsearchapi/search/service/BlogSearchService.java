package com.metalbono.blogsearchapi.search.service;

import com.metalbono.blogsearchapi.search.model.BlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import com.metalbono.blogsearchapi.search.model.exception.OpenApiUnavailableException;
import com.metalbono.blogsearchapi.search.model.request.BlogSearchRequest;
import feign.FeignException;

public interface BlogSearchService<T> {

    BlogSearchSource getSource();

    default BlogSearchResponse searchBlog(BlogSearchRequest request) {
        T result;
        try {
            result = search(request);
        } catch (FeignException e) {
            switch (e.status()) {
                case 429, 500, 502, 503 -> throw new OpenApiUnavailableException(e);
                default -> throw e;
            }
        }
        return convertToBlogSearchResponse(result);
    }

    T search(BlogSearchRequest request);

    BlogSearchResponse convertToBlogSearchResponse(T response);

}
