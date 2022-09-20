package com.metalbono.blogsearchapi.search.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OpenApiReturnsNothingException extends RuntimeException {
    public OpenApiReturnsNothingException(Throwable cause) {
        super(cause);
    }
}
