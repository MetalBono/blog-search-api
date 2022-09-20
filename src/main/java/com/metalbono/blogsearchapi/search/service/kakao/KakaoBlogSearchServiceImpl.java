package com.metalbono.blogsearchapi.search.service.kakao;

import com.metalbono.blogsearchapi.search.client.kakao.KakaoBlogSearchApiClient;
import com.metalbono.blogsearchapi.search.client.kakao.KakaoBlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogContent;
import com.metalbono.blogsearchapi.search.model.BlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import com.metalbono.blogsearchapi.search.model.exception.OpenApiReturnsNothingException;
import com.metalbono.blogsearchapi.search.model.request.BlogSearchRequest;
import com.metalbono.blogsearchapi.search.service.BlogSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KakaoBlogSearchServiceImpl implements BlogSearchService<KakaoBlogSearchResponse> {

    private final KakaoBlogSearchApiClient kakaoBlogSearchApiClient;

    @Override
    public BlogSearchSource getSource() {
        return BlogSearchSource.KAKAO;
    }

    @Override
    public KakaoBlogSearchResponse search(BlogSearchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("요청 객체는 null 이어선 안됩니다.");
        }
        return kakaoBlogSearchApiClient.searchBlog(request);
    }

    @Override
    public BlogSearchResponse convertToBlogSearchResponse(KakaoBlogSearchResponse response) {
        if (response == null) {
            throw new OpenApiReturnsNothingException();
        }
        return BlogSearchResponse.builder()
                .source(getSource())
                .totalCount(response.getMeta().getTotal_count())
                .hasNext(!response.getMeta().getIs_end())
                .blogContents(convertToBlogContents(response.getDocuments()))
                .build();
    }

    private List<BlogContent> convertToBlogContents(List<KakaoBlogSearchResponse.Document> documents) {
        return Optional.ofNullable(documents)
                .orElse(Collections.emptyList())
                .stream()
                .map(doc -> BlogContent.builder()
                        .title(doc.getTitle())
                        .contents(doc.getContents())
                        .blogUrl(doc.getUrl())
                        .blogName(doc.getBlogname())
                        .postDate(doc.getDatetime() == null ? "" : ZonedDateTime.parse(doc.getDatetime()).toLocalDateTime().format(DateTimeFormatter.ISO_DATE))
                        .metadata(new HashMap<>() {{
                            put("thumbnail", doc.getThumbnail());
                        }})
                        .build())
                .collect(Collectors.toList());
    }
}
