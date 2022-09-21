package com.metalbono.blogsearchapi.search.service.aspect;

import com.metalbono.blogsearchapi.search.model.exception.OpenApiUnavailableException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BlogSearchExceptionHandlerAspect {

    @Pointcut("execution(* com.metalbono.blogsearchapi.search.service.BlogSearchService.searchBlog(..))")
    private void searchBlogPointCut() { }

    @AfterThrowing(value = "searchBlogPointCut()", throwing = "t")
    public void failSearch(Throwable t) throws Throwable {
        if (t instanceof FeignException) {
            FeignException fe = (FeignException) t;
            switch (fe.status()) {
                case 429, 500, 502, 503 -> throw new OpenApiUnavailableException(fe);
                default -> throw fe;
            }
        }
        throw t;
    }
}
