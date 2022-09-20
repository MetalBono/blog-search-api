package com.metalbono.blogsearchapi.search.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum BlogSearchSource {
    KAKAO(Map.of("sim", "accuracy", "date", "recency")),
    NAVER(Map.of("accuracy", "sim", "recency", "date"));

    private final Map<String, String> sortMapping;

    public BlogSearchSource getFallbackType() {
        switch (this) {
            case KAKAO -> { return NAVER; }
            case NAVER -> { return KAKAO; }
            default -> { return null; }
        }
    }

    public String getMappedSort(String sort) {
        return Optional.ofNullable(sort)
                .map(sortMapping::get)
                .orElse(null);
    }
}
