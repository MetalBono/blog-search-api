package com.metalbono.blogsearchapi.search.service.kakao;

import com.metalbono.blogsearchapi.search.client.kakao.KakaoBlogSearchApiClient;
import com.metalbono.blogsearchapi.search.client.kakao.KakaoBlogSearchResponse;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KakaoBlogSearchServiceImplTest {

    @Mock
    private KakaoBlogSearchApiClient kakaoBlogSearchApiClient;
    @InjectMocks
    private KakaoBlogSearchServiceImpl kakaoBlogSearchService;

    @Test
    @DisplayName("Open API 검색 소스 정보 조회")
    public void testGetSource() {
        // when
        BlogSearchSource source = kakaoBlogSearchService.getSource();

        // then
        assertEquals(source, BlogSearchSource.KAKAO);
    }

    @Test
    @DisplayName("블로그 검색 Open API 요청 테스트")
    public void testSearch() {
        // given
        BlogSearchRequest request = mock(BlogSearchRequest.class);
        KakaoBlogSearchResponse expected = mock(KakaoBlogSearchResponse.class);
        when(kakaoBlogSearchApiClient.searchBlog(request)).thenReturn(expected);

        // when
        KakaoBlogSearchResponse actual = kakaoBlogSearchService.search(request);

        // then
        assertEquals(expected, actual);
        verify(kakaoBlogSearchApiClient, times(1)).searchBlog(request);
    }

    @Test
    @DisplayName("블로그 검색 테스트 - 정상")
    public void testSearchBlog() {
        // given
        BlogSearchRequest request = mock(BlogSearchRequest.class);
        KakaoBlogSearchResponse kakaoBlogSearchResponse = mock(KakaoBlogSearchResponse.class);
        List<KakaoBlogSearchResponse.Document> documents = new ArrayList<>();
        documents.add(mock(KakaoBlogSearchResponse.Document.class));
        documents.add(mock(KakaoBlogSearchResponse.Document.class));
        documents.add(mock(KakaoBlogSearchResponse.Document.class));
        when(kakaoBlogSearchResponse.getDocuments()).thenReturn(documents);
        KakaoBlogSearchResponse.Meta meta = mock(KakaoBlogSearchResponse.Meta.class);
        int totalCount = new Random().nextInt();
        when(meta.getTotal_count()).thenReturn(totalCount);
        when(kakaoBlogSearchResponse.getMeta()).thenReturn(meta);
        when(kakaoBlogSearchApiClient.searchBlog(request)).thenReturn(kakaoBlogSearchResponse);

        // when
        BlogSearchResponse response = kakaoBlogSearchService.searchBlog(request);

        // then
        assertEquals(response.getTotalCount(), kakaoBlogSearchResponse.getMeta().getTotal_count());
        assertEquals(response.getSource(), BlogSearchSource.KAKAO);
        assertEquals(response.getBlogContents().size(), kakaoBlogSearchResponse.getDocuments().size());
    }

    @Test
    @DisplayName("블로그 검색 테스트 - 에러")
    public void testSearchBlogError() {
        // given
        BlogSearchRequest request = mock(BlogSearchRequest.class);
        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(429);
        when(kakaoBlogSearchApiClient.searchBlog(request)).thenThrow(feignException);

        // when
        try {
            kakaoBlogSearchService.searchBlog(request);
        } catch (Exception e) {
            // then
            assertTrue(true);
            return;
        }
        assertFalse(true);
    }
}
