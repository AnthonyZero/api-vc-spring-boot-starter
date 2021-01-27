package com.anthonyzero.api.vc;

public class ApiControlException extends RuntimeException {

    public ApiControlException(String message) {
        super(message);
    }

    public ApiControlException(String message, Throwable cause) {
        super(message, cause);
    }
}
