package com.metalbono.blogsearchapi.search.service.naver;

import com.metalbono.blogsearchapi.search.client.naver.NaverBlogSearchApiClient;
import com.metalbono.blogsearchapi.search.client.naver.NaverBlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogContent;
import com.metalbono.blogsearchapi.search.model.BlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import com.metalbono.blogsearchapi.search.model.exception.OpenApiReturnsNothingException;
import com.metalbono.blogsearchapi.search.model.request.BlogSearchRequest;
import com.metalbono.blogsearchapi.search.service.BlogSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NaverBlogSearchServiceImpl implements BlogSearchService<NaverBlogSearchResponse> {

    private final NaverBlogSearchApiClient naverBlogSearchApiClient;

    @Override
    public BlogSearchSource getSource() {
        return BlogSearchSource.NAVER;
    }

    @Override
    public NaverBlogSearchResponse search(BlogSearchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("요청 객체는 null 이어선 안됩니다.");
        }
        return naverBlogSearchApiClient.searchBlog(request.getQuery(), request.getSort(), request.getPage(), request.getSize());
    }

    @Override
    public BlogSearchResponse convertToBlogSearchResponse(NaverBlogSearchResponse response) {
        if (response == null) {
            throw new OpenApiReturnsNothingException();
        }
        return BlogSearchResponse.builder()
                .source(BlogSearchSource.NAVER)
                .totalCount(response.getTotal())
                .hasNext(response.getTotal() - (response.getDisplay() * response.getStart()) > 0)
                .blogContents(convertToBlogContents(response.getItems()))
                .build();
    }

    private List<BlogContent> convertToBlogContents(List<NaverBlogSearchResponse.Item> items) {
        return Optional.ofNullable(items)
                .orElse(Collections.emptyList())
                .stream()
                .map(item -> BlogContent.builder()
                        .title(item.getTitle())
                        .contents(item.getDescription())
                        .blogUrl(item.getBloggerlink())
                        .blogName(item.getBloggername())
                        .postDate(item.getPostdate() == null ? null : LocalDate.parse(item.getPostdate(), DateTimeFormatter.ofPattern("yyyyMMdd")).format(DateTimeFormatter.ISO_DATE))
                        .metadata(new HashMap<>() {{
                            put("link", item.getLink());
                        }})
                        .build())
                .collect(Collectors.toList());
    }
}
