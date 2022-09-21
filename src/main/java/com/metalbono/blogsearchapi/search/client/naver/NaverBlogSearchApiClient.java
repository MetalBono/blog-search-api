package com.metalbono.blogsearchapi.search.client.naver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "naver-blog-search",
        url = "https://openapi.naver.com",
        configuration = NaverBlogSearchApiConfig.class
)
public interface NaverBlogSearchApiClient {

    @GetMapping("/v1/search/blog.json")
    NaverBlogSearchResponse searchBlog(@RequestParam("query") String query, @RequestParam("sort") String sort,
                                       @RequestParam("start") int start, @RequestParam("display") int display);
}
