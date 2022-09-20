package com.metalbono.blogsearchapi.search.producer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.BlockingQueue;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BlogSearchKeywordProducerTest {

    @Mock
    private BlockingQueue<String> searchKeywordQueue;
    @InjectMocks
    private BlogSearchKeywordProducer blogSearchKeywordProducer;

    @Test
    @DisplayName("검색어 집계 처리 요청 테스트")
    public void testProcessAsync() {
        // given
        String query = "http://도메인/이름 블로그에서 검색을 한다.";

        // when
        blogSearchKeywordProducer.processAsync(query);

        // then
        verify(searchKeywordQueue, times(3)).add(anyString());
    }
}
