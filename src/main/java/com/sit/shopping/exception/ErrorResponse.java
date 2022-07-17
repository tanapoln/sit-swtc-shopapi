package com.sit.shopping.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private int code;
    private String message;

    public ErrorResponse(String message, int status) {
        this.code = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
