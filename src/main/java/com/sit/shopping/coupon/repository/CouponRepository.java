package com.sit.shopping.coupon.repository;

import com.sit.shopping.coupon.model.Coupon;

public interface CouponRepository {
    Coupon findByCoupon(String couponCode);
}
