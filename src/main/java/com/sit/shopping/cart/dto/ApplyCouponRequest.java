package com.sit.shopping.cart.dto;

public class ApplyCouponRequest {
    private String cartId;
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
