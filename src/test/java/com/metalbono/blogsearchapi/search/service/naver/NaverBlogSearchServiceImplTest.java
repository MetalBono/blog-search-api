package com.metalbono.blogsearchapi.search.service.naver;

import com.metalbono.blogsearchapi.search.client.naver.NaverBlogSearchApiClient;
import com.metalbono.blogsearchapi.search.client.naver.NaverBlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogSearchResponse;
import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import com.metalbono.blogsearchapi.search.model.request.BlogSearchRequest;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NaverBlogSearchServiceImplTest {

    @Mock
    private NaverBlogSearchApiClient naverBlogSearchApiClient;
    @InjectMocks
    private NaverBlogSearchServiceImpl naverBlogSearchService;

    @Test
    @DisplayName("Open API 검색 소스 정보 조회")
    public void testGetSource() {
        // when
        BlogSearchSource source = naverBlogSearchService.getSource();

        // then
        assertEquals(source, BlogSearchSource.NAVER);
    }

    @Test
    @DisplayName("블로그 검색 Open API 요청 테스트")
    public void testSearch() {
        // given
        BlogSearchRequest request = mock(BlogSearchRequest.class);
        when(request.getQuery()).thenReturn(UUID.randomUUID().toString());
        when(request.getSort()).thenReturn(UUID.randomUUID().toString());
        NaverBlogSearchResponse expected = mock(NaverBlogSearchResponse.class);
        when(naverBlogSearchApiClient.searchBlog(anyString(), anyString(), anyInt(), anyInt())).thenReturn(expected);

        // when
        NaverBlogSearchResponse actual = naverBlogSearchService.search(request);

        // then
        assertEquals(expected, actual);
        verify(naverBlogSearchApiClient, times(1)).searchBlog(anyString(), anyString(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("블로그 검색 테스트 - 정상")
    public void testSearchBlog() {
        // given
        BlogSearchRequest request = mock(BlogSearchRequest.class);
        when(request.getQuery()).thenReturn(UUID.randomUUID().toString());
        when(request.getSort()).thenReturn(UUID.randomUUID().toString());
        NaverBlogSearchResponse naverBlogSearchResponse = mock(NaverBlogSearchResponse.class);
        List<NaverBlogSearchResponse.Item> items = new ArrayList<>();
        items.add(mock(NaverBlogSearchResponse.Item.class));
        items.add(mock(NaverBlogSearchResponse.Item.class));
        items.add(mock(NaverBlogSearchResponse.Item.class));
        when(naverBlogSearchResponse.getItems()).thenReturn(items);
        when(naverBlogSearchApiClient.searchBlog(anyString(), anyString(), anyInt(), anyInt())).thenReturn(naverBlogSearchResponse);

        // when
        BlogSearchResponse response = naverBlogSearchService.searchBlog(request);

        // then
        assertEquals(response.getTotalCount(), naverBlogSearchResponse.getTotal());
        assertEquals(response.getSource(), BlogSearchSource.NAVER);
        assertEquals(response.getBlogContents().size(), naverBlogSearchResponse.getItems().size());
    }

    @Test
    @DisplayName("블로그 검색 테스트 - 에러")
    public void testSearchBlogError() {
        // given
        BlogSearchRequest request = mock(BlogSearchRequest.class);
        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(429);
        when(naverBlogSearchApiClient.searchBlog(anyString(), anyString(), anyInt(), anyInt())).thenThrow(feignException);

        // when
        try {
            naverBlogSearchService.searchBlog(request);
        } catch (Exception e) {
            // then
            assertTrue(true);
            return;
        }
        assertFalse(true);
    }
}
