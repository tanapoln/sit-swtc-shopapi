package com.sit.shopping.coupon.model;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

public class Coupon {
    private String couponCode;
    private String name;
    private String description;
    private String discountType;
    private BigDecimal discountAmount;
    private BigDecimal minimumAmount;

    public Coupon(String couponCode, String name, String description, String discountType, BigDecimal discountAmount,
            BigDecimal minimumAmount) {
        if (!StringUtils.hasText(couponCode)) {
            throw new IllegalArgumentException("coupon code must not be empty");
        }

        this.couponCode = couponCode;
        this.name = name;
        this.description = description;
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.minimumAmount = minimumAmount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(BigDecimal minimumAmount) {
        this.minimumAmount = minimumAmount;
    }
}
