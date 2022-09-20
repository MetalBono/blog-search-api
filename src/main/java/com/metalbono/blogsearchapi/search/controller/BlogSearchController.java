package com.metalbono.blogsearchapi.search.controller;

import com.metalbono.blogsearchapi.search.producer.BlogSearchKeywordProducer;
import com.metalbono.blogsearchapi.search.model.BlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import com.metalbono.blogsearchapi.search.model.exception.OpenApiUnavailableException;
import com.metalbono.blogsearchapi.search.model.request.BlogSearchRequest;
import com.metalbono.blogsearchapi.search.service.BlogSearchServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blog/search")
@RequiredArgsConstructor
public class BlogSearchController {

    private final BlogSearchServiceFactory blogSearchServiceFactory;
    private final BlogSearchKeywordProducer blogSearchKeywordProducer;

    @GetMapping
    public BlogSearchResponse searchBlog(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "source", required = false, defaultValue = "KAKAO") BlogSearchSource source,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        // 인기 검색어 처리를 위한 비동기 호출
        blogSearchKeywordProducer.processAsync(query);

        try {
            return blogSearchServiceFactory.getService(source)
                    .searchBlog(BlogSearchRequest.builder()
                            .query(query)
                            .sort(sort)
                            .page(page)
                            .size(size)
                            .build());
        } catch (OpenApiUnavailableException e) {
            BlogSearchSource fallbackSource = source.getFallbackType();
            return blogSearchServiceFactory.getService(fallbackSource)
                    .searchBlog(BlogSearchRequest.builder()
                            .query(query)
                            .sort(fallbackSource.getMappedSort(sort))
                            .page(page)
                            .size(size)
                            .build());
        }
    }
}
