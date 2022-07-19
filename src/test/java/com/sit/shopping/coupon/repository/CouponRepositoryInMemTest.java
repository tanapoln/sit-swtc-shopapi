package com.sit.shopping.coupon.repository;

import com.sit.shopping.coupon.model.Coupon;
import com.sit.shopping.exception.InvalidCouponException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CouponRepositoryInMemTest {
    private CouponRepositoryInMem underTest;

    @BeforeEach
    void setUp() {
        underTest = new CouponRepositoryInMem();
    }

    @Test
    void testFindByCouponId() {
        String couponCode = "TGIF20";

        Coupon coupon = underTest.findByCoupon(couponCode);

        MatcherAssert.assertThat(coupon.getCouponCode(), CoreMatchers.equalTo(couponCode));
    }

    @Test
    void testFindByInvalidCouponId() {
        String couponCode = "iNvLiDCoP0n-x-INSERT INTO (%";

        Assertions.assertThrows(InvalidCouponException.class, () -> {
            underTest.findByCoupon(couponCode);
        });
    }

    @Test
    void testFindByNullCouponId() {
        Assertions.assertThrows(InvalidCouponException.class, () -> {
            underTest.findByCoupon(null);
        });
    }
}