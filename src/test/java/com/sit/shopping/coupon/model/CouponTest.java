package com.sit.shopping.coupon.model;

import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.exception.InvalidCouponException;
import com.sit.shopping.product.model.Product;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

class CouponTest {
    private static class SuccessCaseTestData {
        private Cart cart;
        private Coupon coupon;
        public BigDecimal expectedDiscount;
        private int usageTime;

        public static SuccessCaseTestData create(Cart cart, Coupon coupon, BigDecimal expectedDiscount, int usageTime) {
            SuccessCaseTestData testData = new SuccessCaseTestData();
            testData.cart = cart;
            testData.coupon = coupon;
            testData.expectedDiscount = expectedDiscount;
            testData.usageTime = usageTime;
            return testData;
        }
    }

    private static Stream<Arguments> provideCouponSuccessTestCases() {
        Product product1 = Product.create("Loose Cropped Jeans (Damaged)", 42.57,
                "https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/448429/sub/goods_448429_sub13"
                        + ".jpg?width=1600&impolicy=quality_75");
        Product product2 = Product.create("Smart Skort Solid", 140.37,
                "https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/455844/sub/goods_455844_sub14"
                        + ".jpg?width=1600&impolicy=quality_75");

        Cart cartSS7 = Cart.create(UUID.randomUUID().toString());
        cartSS7.addProduct(product1);
        Named<Object> ss7 = Named.of("SS-7 no coupon apply by default", SuccessCaseTestData.create(cartSS7, null, BigDecimal.ZERO, 1));

        Cart cartSS8 = Cart.create(UUID.randomUUID().toString());
        cartSS8.addProduct(product2);
        Coupon couponSS8 = Coupon.create("TGIF20", "Discount $20", "Get $20 discount when you order $60 minimum", "FIXED_AMOUNT",
                new BigDecimal("20"), new BigDecimal("60"));
        Named<Object> ss8 = Named.of("SS-8 TGIF20 with items more than $60", SuccessCaseTestData.create(cartSS8, couponSS8, BigDecimal.valueOf(20), 1));

        Cart cartSS10 = Cart.create(UUID.randomUUID().toString());
        cartSS10.addProduct(product2);
        Coupon couponSS10 = Coupon.create("", "Fallback Discoun", "Get $0 discount when you order $60 minimum", "FIXED_AMOUNT",
                new BigDecimal("20"), new BigDecimal("60"));
        Named<Object> ss10 = Named.of("SS-10 Remove coupon when apply an empty coupon", SuccessCaseTestData.create(cartSS10, couponSS10, BigDecimal.ZERO, 1));

        Cart cartSS13 = Cart.create(UUID.randomUUID().toString());
        cartSS13.addProduct(product2);
        Coupon couponSS13 = Coupon.create("TGIF20", "Discount $20", "Get $20 discount when you order $60 minimum", "FIXED_AMOUNT",
                new BigDecimal("20"), new BigDecimal("60"));
        Named<Object> ss13 = Named.of("SS-13 Apply same coupon multiple times", SuccessCaseTestData.create(cartSS13, couponSS13, BigDecimal.valueOf(20), 3));

        return Stream.of(
                Arguments.of(ss7),
                Arguments.of(ss8),
                Arguments.of(ss10),
                Arguments.of(ss13)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCouponSuccessTestCases")
    void testApplyCouponSuccess(SuccessCaseTestData data) {
        if (data.coupon != null) {
            for (int i = 0; i < data.usageTime; i++) {
                data.coupon.applyToCart(data.cart);
            }
        }

        MatcherAssert.assertThat(data.cart.getDiscountAmount(), Matchers.comparesEqualTo(data.expectedDiscount));
    }

    private static class FailedCaseTestData {
        private Cart cart;
        public Coupon coupon;
        private Exception expectedException;

        public static FailedCaseTestData create(Cart cart, Coupon coupon, Exception expectedException) {
            FailedCaseTestData testData = new FailedCaseTestData();
            testData.cart = cart;
            testData.coupon = coupon;
            testData.expectedException = expectedException;
            return testData;
        }
    }

    private static Stream<Arguments> provideCouponFailedTestCases() {
        Coupon coupon = Coupon.create("TGIF20", "Discount $20", "Get $20 discount when you order $60 minimum", "FIXED_AMOUNT",
                new BigDecimal("20"), new BigDecimal("60"));

        Product product = Product.create("Loose Cropped Jeans (Damaged)", 42.57,
                "https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/448429/sub/goods_448429_sub13"
                        + ".jpg?width=1600&impolicy=quality_75");

        Cart cartSS9 = Cart.create(UUID.randomUUID().toString());
        cartSS9.addProduct(product);
        Named<Object> ss9 = Named.of("SS-9 TGIF20 with items less than $60", FailedCaseTestData.create(cartSS9, coupon, new InvalidCouponException()));

        Named<Object> edgeCase = Named.of("edgeCase-1 apply coupon to null cart", FailedCaseTestData.create(null, coupon, new IllegalArgumentException("A cart must not be null")));

        return Stream.of(
                Arguments.of(ss9),
                Arguments.of(edgeCase)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCouponFailedTestCases")
    void testApplyCouponFailed(FailedCaseTestData testData) {
        Assertions.assertThrows(testData.expectedException.getClass(), () -> {
            testData.coupon.applyToCart(testData.cart);
        });
    }
}