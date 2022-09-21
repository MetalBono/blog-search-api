package com.metalbono.blogsearchapi.popularwords.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalbono.blogsearchapi.popluarwords.controller.PopularWordsApiController;
import com.metalbono.blogsearchapi.popluarwords.service.PopularWordsService;
import com.metalbono.blogsearchapi.statistics.model.KeywordSearchStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(PopularWordsApiController.class)
public class PopularWordsApiControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private PopularWordsService popularWordsService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("인기 검색어 목록 조회 API 호출")
    public void testGetPopularWordsTop10() throws Exception {
        // given
        List<KeywordSearchStatistics> expected = new ArrayList<>();
        expected.add(new KeywordSearchStatistics());
        expected.add(new KeywordSearchStatistics());
        when(popularWordsService.getPopularWordsTop10()).thenReturn(expected);

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/popular-words/top10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        KeywordSearchStatistics[] actual = objectMapper.readValue(result.getResponse().getContentAsString(), KeywordSearchStatistics[].class);

        // then
        assertEquals(actual.length, expected.size());
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i].getKeyword(), expected.get(i).getKeyword());
            assertEquals(actual[i].getCount(), expected.get(i).getCount());
        }
    }
}
