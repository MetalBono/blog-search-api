package com.metalbono.blogsearchapi.search.client.kakao;

import com.metalbono.blogsearchapi.search.model.request.BlogSearchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "kakao-blog-search",
        url = "https://dapi.kakao.com",
        configuration = KakaoBlogSearchApiConfig.class
)
public interface KakaoBlogSearchApiClient {

    @GetMapping("/v2/search/blog")
    KakaoBlogSearchResponse searchBlog(@SpringQueryMap BlogSearchRequest blogSearchRequest);
}
