package com.metalbono.blogsearchapi.search.controller;

import com.metalbono.blogsearchapi.search.model.BlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogSearchSortType;
import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import com.metalbono.blogsearchapi.search.model.exception.OpenApiUnavailableException;
import com.metalbono.blogsearchapi.search.model.request.BlogSearchRequest;
import com.metalbono.blogsearchapi.search.producer.BlogSearchKeywordProducer;
import com.metalbono.blogsearchapi.search.service.BlogSearchServiceFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "blog-search", description = "블로그 검색 API")
@RestController
@RequestMapping("/api/blog/search")
@RequiredArgsConstructor
public class BlogSearchController {

    private final BlogSearchServiceFactory blogSearchServiceFactory;
    private final BlogSearchKeywordProducer blogSearchKeywordProducer;

    @Operation(summary = "블로그 검색", description = "Open API 를 이용하여 블로그 문서를 검색한다.")
    @Parameters({
            @Parameter(name = "query", description = "검색어", example = "티셔츠"),
            @Parameter(name = "sort", description = "정렬 기준", example = "ACCURACY"),
            @Parameter(name = "source", description = "Open API 소스", example = "KAKAO"),
            @Parameter(name = "page", description = "페이지 번호", example = "1"),
            @Parameter(name = "size", description = "페이지 크기", example = "30")
    })
    @GetMapping
    public BlogSearchResponse searchBlog(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "sort", required = false) BlogSearchSortType sort,
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
                            .sort(source.getSortStr(sort))
                            .page(page)
                            .size(size)
                            .build());
        } catch (OpenApiUnavailableException e) {
            BlogSearchSource fallbackSource = source.getFallbackType();
            if (fallbackSource == null) {
                throw e;
            }
            return blogSearchServiceFactory.getService(fallbackSource)
                    .searchBlog(BlogSearchRequest.builder()
                            .query(query)
                            .sort(fallbackSource.getSortStr(sort))
                            .page(page)
                            .size(size)
                            .build());
        }
    }
}
