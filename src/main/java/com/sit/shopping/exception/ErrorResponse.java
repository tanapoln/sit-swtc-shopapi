package com.sit.shopping.exception;

public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(String message, int status) {
        this.code = String.format("%d", status);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
