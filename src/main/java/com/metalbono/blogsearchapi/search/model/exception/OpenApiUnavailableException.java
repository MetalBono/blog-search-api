package com.metalbono.blogsearchapi.search.model.exception;

import lombok.NoArgsConstructor;

/**
 * Open API 에 장애가 발생한 경우 발생하는 예외
 */
@NoArgsConstructor
public class OpenApiUnavailableException extends RuntimeException {
    public OpenApiUnavailableException(Throwable cause) {
        super(cause);
    }
}
