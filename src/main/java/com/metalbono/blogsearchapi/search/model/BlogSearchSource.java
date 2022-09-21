package com.metalbono.blogsearchapi.search.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlogSearchSource {
    KAKAO("accuracy", "recency"),
    NAVER("sim", "date");

    private final String sortAccuracy;
    private final String sortLatest;

    public BlogSearchSource getFallbackType() {
        switch (this) {
            case KAKAO -> { return NAVER; }
            case NAVER -> { return KAKAO; }
            default -> { return null; }
        }
    }

    public String getSortStr(BlogSearchSortType sortType) {
        if (sortType == null) {
            return null;
        }
        switch (sortType) {
            case ACCURACY -> { return this.sortAccuracy; }
            case LATEST -> { return this.sortLatest; }
            default -> { return null; }
        }
    }
}
