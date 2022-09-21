package com.metalbono.blogsearchapi.search.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalbono.blogsearchapi.search.producer.BlogSearchKeywordProducer;
import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import com.metalbono.blogsearchapi.search.model.exception.OpenApiUnavailableException;
import com.metalbono.blogsearchapi.search.model.request.BlogSearchRequest;
import com.metalbono.blogsearchapi.search.service.BlogSearchService;
import com.metalbono.blogsearchapi.search.service.BlogSearchServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(BlogSearchController.class)
public class BlogSearchControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private BlogSearchServiceFactory blogSearchServiceFactory;
    @MockBean
    private BlogSearchKeywordProducer blogSearchKeywordProducer;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("블로그 검색 테스트 - 정상")
    public void testSearchBlog() throws Exception {
        // given
        String query = UUID.randomUUID().toString();
        BlogSearchService blogSearchService = mock(BlogSearchService.class);
        when(blogSearchServiceFactory.getService(any())).thenReturn(blogSearchService);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/blog/search")
                        .param("query", query)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        verify(blogSearchKeywordProducer, times(1)).processAsync(query);
        verify(blogSearchService, times(1)).searchBlog(any(BlogSearchRequest.class));
    }

    @Test
    @DisplayName("블로그 검색 테스트 - 에러 발생 후 fallback 실행")
    public void testSearchBlogFallback() throws Exception {
        // given
        String query = UUID.randomUUID().toString();
        BlogSearchSource source = BlogSearchSource.KAKAO;
        BlogSearchService blogSearchService = mock(BlogSearchService.class);
        when(blogSearchServiceFactory.getService(source)).thenReturn(blogSearchService);
        when(blogSearchService.searchBlog(any())).thenThrow(new OpenApiUnavailableException());
        BlogSearchService fallbackService = mock(BlogSearchService.class);
        when(blogSearchServiceFactory.getService(source.getFallbackType())).thenReturn(fallbackService);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/blog/search")
                        .param("query", query)
                        .param("source", source.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        verify(blogSearchKeywordProducer, times(1)).processAsync(query);
        verify(blogSearchService, times(1)).searchBlog(any(BlogSearchRequest.class));
        verify(fallbackService, times(1)).searchBlog(any(BlogSearchRequest.class));
    }
}
