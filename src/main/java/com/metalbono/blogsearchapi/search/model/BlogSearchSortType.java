package com.metalbono.blogsearchapi.search.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlogSearchSortType {
    ACCURACY("정확도순"),
    LATEST("최신순");

    private final String description;
}
