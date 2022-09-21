package com.metalbono.blogsearchapi.search.service;

import com.metalbono.blogsearchapi.search.model.BlogSearchSource;
import com.metalbono.blogsearchapi.search.service.kakao.KakaoBlogSearchServiceImpl;
import com.metalbono.blogsearchapi.search.service.naver.NaverBlogSearchServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlogSearchServiceFactoryTest {

    @Test
    @DisplayName("카카오 서비스 가져오기 테스트")
    public void testGetKakaoService() {
        // given
        BlogSearchSource source = BlogSearchSource.KAKAO;
        List<BlogSearchService> blogSearchServices = new ArrayList<>();
        BlogSearchService kakaoService = mock(KakaoBlogSearchServiceImpl.class);
        when(kakaoService.getSource()).thenReturn(source);
        blogSearchServices.add(kakaoService);
        BlogSearchServiceFactory blogSearchServiceFactory = new BlogSearchServiceFactory(blogSearchServices);

        // when
        BlogSearchService actual = blogSearchServiceFactory.getService(source);

        // then
        assertInstanceOf(KakaoBlogSearchServiceImpl.class, actual);
    }

    @Test
    @DisplayName("네이버 서비스 가져오기 테스트")
    public void testGetNaverService() {
        // given
        BlogSearchSource source = BlogSearchSource.NAVER;
        List<BlogSearchService> blogSearchServices = new ArrayList<>();
        BlogSearchService naverService = mock(NaverBlogSearchServiceImpl.class);
        when(naverService.getSource()).thenReturn(source);
        blogSearchServices.add(naverService);
        BlogSearchServiceFactory blogSearchServiceFactory = new BlogSearchServiceFactory(blogSearchServices);

        // when
        BlogSearchService actual = blogSearchServiceFactory.getService(source);

        // then
        assertInstanceOf(NaverBlogSearchServiceImpl.class, actual);
    }
}
