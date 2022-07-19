package com.sit.shopping.coupon.repository;

import com.sit.shopping.coupon.model.Coupon;
import com.sit.shopping.exception.InvalidCouponException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;

@Repository
public class CouponRepositoryInMem implements CouponRepository {
    private HashMap<String, Coupon> couponMap;

    public CouponRepositoryInMem() {
        if (couponMap == null) {
            couponMap = new HashMap<>();
        }

        Coupon coupon = new Coupon("TGIF20", "Discount $20", "Get $20 discount when you order $60 minimum", "FIXED_AMOUNT", new BigDecimal("20"), new BigDecimal("60"));
        couponMap.putIfAbsent(coupon.getCouponCode(), coupon);
    }

    @Override
    public Coupon findByCoupon(String couponCode) {
        Coupon coupon = couponMap.get(couponCode);

        if (coupon == null) {
            throw new InvalidCouponException();
        }

        return coupon;
    }
}
