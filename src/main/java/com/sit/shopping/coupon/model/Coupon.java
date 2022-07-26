package com.sit.shopping.coupon.model;

import java.math.BigDecimal;

import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.exception.InvalidCouponException;
import org.springframework.util.StringUtils;

public class Coupon {
    private String couponCode;
    private String name;
    private String description;
    private String discountType;
    private BigDecimal discountAmount;
    private BigDecimal minimumAmount;

    public static Coupon create(String couponCode, String name, String description, String discountType, BigDecimal discountAmount,
            BigDecimal minimumAmount) {
        if (couponCode == null) {
            throw new IllegalArgumentException("coupon code must not be empty");
        }

        Coupon coupon = new Coupon();
        coupon.couponCode = couponCode;
        coupon.name = name;
        coupon.description = description;
        coupon.discountType = discountType;
        coupon.discountAmount = discountAmount;
        coupon.minimumAmount = minimumAmount;
        return coupon;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getName() {
        return name;
    }

    public String getDiscountType() {
        return discountType;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount;
    }

    public boolean isEmptyCoupon() {
        return !StringUtils.hasText(couponCode);
    }

    public void applyToCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("A cart must not be null");
        }

        if (isEmptyCoupon()) {
            cart.removeCoupon();

            return;
        }

        if (cart.getSubtotal().compareTo(minimumAmount) < 0) {
            throw new InvalidCouponException();
        }

        cart.applyCoupon(this);
    }
}
