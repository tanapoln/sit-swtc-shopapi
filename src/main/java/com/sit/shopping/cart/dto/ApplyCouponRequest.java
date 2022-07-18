package com.sit.shopping.cart.dto;

import javax.validation.constraints.NotNull;

public class ApplyCouponRequest {
    @NotNull
    private String cartId;
    @NotNull
    private String coupon;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
