package com.sit.shopping.cart.dto;

import com.sit.shopping.cart.model.Cart;

import java.math.BigDecimal;

public class ApplyCouponResponse {
    private boolean success;
    private String description;

    public ApplyCouponResponse(String description) {
        this.success = true;
        this.description = description;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
