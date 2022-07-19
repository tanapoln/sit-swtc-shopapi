package com.sit.shopping.cart.model;

import com.sit.shopping.coupon.model.Coupon;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart underTest;

    @BeforeEach
    void setUp() {
        underTest = new Cart();
    }

    @Test
    void testApplyCouponSuccess() {
        Coupon coupon = new Coupon("TGIF20", "20$ discount", "Get discount 20$ so good campaign", "FIXED_AMOUNT", new BigDecimal("20"), new BigDecimal("60"));

        underTest.applyCoupon(coupon);

        MatcherAssert.assertThat(underTest.getDiscountAmount(), Matchers.comparesEqualTo(new BigDecimal("20")));
        MatcherAssert.assertThat(underTest.getDiscountName(), Matchers.comparesEqualTo(coupon.getName()));
        MatcherAssert.assertThat(underTest.getDiscountDescription(), Matchers.comparesEqualTo(coupon.getDescription()));
    }

    @Test
    void testRemoveCoupon() {
        Coupon coupon = new Coupon("TGIF20", "20$ discount", "Get discount 20$ so good campaign", "FIXED_AMOUNT", new BigDecimal("20"), new BigDecimal("60"));

        underTest.applyCoupon(coupon);

        underTest.removeCoupon();

        MatcherAssert.assertThat(underTest.getDiscountAmount(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        MatcherAssert.assertThat(underTest.getDiscountName(), Matchers.nullValue());
        MatcherAssert.assertThat(underTest.getDiscountDescription(), Matchers.nullValue());
    }
}