package com.metalbono.blogsearchapi.search.service;

import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BlogSearchServiceFactory {

    private final Map<BlogSearchSource, BlogSearchService> blogServiceMap = new HashMap<>();

    public BlogSearchServiceFactory(List<BlogSearchService> blogSearchServices) {
        if(CollectionUtils.isEmpty(blogSearchServices)) {
            throw new IllegalArgumentException("존재하는 BlogSearchService 구현체가 없음");
        }

        for (BlogSearchService service : blogSearchServices) {
            this.blogServiceMap.put(service.getSource(), service);
        }
    }


    public BlogSearchService getService(BlogSearchSource source) {
        if (source == null) {
            throw new IllegalArgumentException("BlogSearchService 의 타입은 null 이어선 안됩니다.");
        }
        return blogServiceMap.get(source);
    }
}
