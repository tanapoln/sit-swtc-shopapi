package com.sit.shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCouponException extends RuntimeException {
    public InvalidCouponException() {
        super("Coupon cannot be applied due to expired or invalid");
    }
}
