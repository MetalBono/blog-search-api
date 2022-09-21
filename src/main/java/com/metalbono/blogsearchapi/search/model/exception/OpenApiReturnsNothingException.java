package com.metalbono.blogsearchapi.search.model.exception;

import lombok.NoArgsConstructor;

/**
 * Open API 에서 응답값을 제공하지 않은 경우 발생하는 예외
 */
@NoArgsConstructor
public class OpenApiReturnsNothingException extends RuntimeException {
    public OpenApiReturnsNothingException(Throwable cause) {
        super(cause);
    }
}
