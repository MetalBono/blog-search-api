package com.metalbono.blogsearchapi.search.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OpenApiUnavailableException extends RuntimeException {
    public OpenApiUnavailableException(Throwable cause) {
        super(cause);
    }
}
