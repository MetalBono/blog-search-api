package com.metalbono.blogsearchapi.popluarwords.controller;

import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import com.metalbono.blogsearchapi.popluarwords.service.PopularWordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/popular-words")
@RequiredArgsConstructor
public class PopularWordsApiController {

    private final PopularWordsService popularWordsService;

    @GetMapping("/top10")
    public List<KeywordSearchStatistics> getPopularWordsTop10() {
        return popularWordsService.getPopularWordsTop10();
    }
}
