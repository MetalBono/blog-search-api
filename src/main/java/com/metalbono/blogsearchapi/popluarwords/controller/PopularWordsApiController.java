package com.metalbono.blogsearchapi.popluarwords.controller;

import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import com.metalbono.blogsearchapi.popluarwords.service.PopularWordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "popular-words", description = "인기검색어 API")
@RestController
@RequestMapping("/api/popular-words")
@RequiredArgsConstructor
public class PopularWordsApiController {

    private final PopularWordsService popularWordsService;

    @Operation(summary = "인기검색어 TOP 10 조회", description = "사용자들이 검색한 단어들 중 인기순 상위 10개 검색어 조회")
    @GetMapping("/top10")
    public List<KeywordSearchStatistics> getPopularWordsTop10() {
        return popularWordsService.getPopularWordsTop10();
    }
}
