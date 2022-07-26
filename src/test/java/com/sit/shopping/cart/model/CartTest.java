package com.sit.shopping.cart.model;

import com.sit.shopping.coupon.model.Coupon;
import com.sit.shopping.product.model.Product;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

class CartTest {
    private String generatedCartId = UUID.randomUUID().toString();

    private static class TestData {
        private Coupon coupon;
        private List<Product> expectedProducts;
        private List<ExpectedEachProduct> expectedEachProducts;
        private BigDecimal expectedSubTotal;
        private BigDecimal expectedTotal;

        public static TestData create(List<Product> expectedProducts, List<ExpectedEachProduct> expectedEachProducts, BigDecimal expectedSubTotal, BigDecimal expectedTotal) {
            TestData testData = new TestData();
            testData.expectedProducts = expectedProducts;
            testData.expectedEachProducts = expectedEachProducts;
            testData.expectedSubTotal = expectedSubTotal;
            testData.expectedTotal = expectedTotal;
            return testData;
        }
    }

    private static class ExpectedEachProduct {
        private BigDecimal expectedEachSubTotal;
        private BigDecimal expectedEachTotal;

        public ExpectedEachProduct(BigDecimal expectedEachSubTotal, BigDecimal expectedEachTotal) {
            this.expectedEachSubTotal = expectedEachSubTotal;
            this.expectedEachTotal = expectedEachTotal;
        }
    }

    private static Stream<Arguments> provideCartTestCases() {
        Coupon coupon = Coupon.create("TGIF20", "Discount $20", "Get $20 discount when you order $60 minimum", "FIXED_AMOUNT",
                new BigDecimal("20"), new BigDecimal("60"));

        Product product1 = Product.create("Loose Cropped Jeans (Damaged)", 42.57,
                "https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/448429/sub/goods_448429_sub13"
                        + ".jpg?width=1600&impolicy=quality_75");
        Product product2 = Product.create("Smart Skort Solid", 140.37,
                "https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/455844/sub/goods_455844_sub14"
                        + ".jpg?width=1600&impolicy=quality_75");

        TestData ss2Data = TestData.create(Collections.emptyList(), Collections.emptyList(), BigDecimal.ZERO, BigDecimal.ZERO);
        Named<TestData> ss2 = Named.of("SS-2 initial cart must be empty", ss2Data);

        ExpectedEachProduct eachProduct1SS3 = new ExpectedEachProduct(BigDecimal.valueOf(42.57), BigDecimal.valueOf(42.57));
        TestData ss3Data = TestData.create(List.of(product1), List.of(eachProduct1SS3), BigDecimal.valueOf(42.57), BigDecimal.valueOf(42.57));
        Named<TestData> ss3 = Named.of("SS-3 cart with 1 item", ss3Data);

        ExpectedEachProduct eachProduct1SS4 = new ExpectedEachProduct(BigDecimal.valueOf(42.57), BigDecimal.valueOf(42.57));
        ExpectedEachProduct eachProduct2SS4 = new ExpectedEachProduct(BigDecimal.valueOf(85.14), BigDecimal.valueOf(85.14));
        List<ExpectedEachProduct> expectedEachProductsSS4 = List.of(eachProduct1SS4, eachProduct2SS4);
        TestData ss4Data = TestData.create(List.of(product1, product1), expectedEachProductsSS4, BigDecimal.valueOf(85.14), BigDecimal.valueOf(85.14));
        Named<TestData> ss4 = Named.of("SS-4 cart with 1 item but more than 1qty", ss4Data);

        ExpectedEachProduct eachProduct1SS5 = new ExpectedEachProduct(BigDecimal.valueOf(42.57), BigDecimal.valueOf(42.57));
        ExpectedEachProduct eachProduct2SS5 = new ExpectedEachProduct(BigDecimal.valueOf(182.94), BigDecimal.valueOf(182.94));
        List<ExpectedEachProduct> expectedEachProductsSS5 = List.of(eachProduct1SS5, eachProduct2SS5);
        TestData ss5Data = TestData.create(List.of(product1, product2), expectedEachProductsSS5, BigDecimal.valueOf(182.94), BigDecimal.valueOf(182.94));
        Named<TestData> ss5 = Named.of("SS-5 cart with multiple items, each with 1qty", ss5Data);

        ExpectedEachProduct eachProduct1SS6 = new ExpectedEachProduct(BigDecimal.valueOf(42.57), BigDecimal.valueOf(42.57));
        ExpectedEachProduct eachProduct2SS6 = new ExpectedEachProduct(BigDecimal.valueOf(182.94), BigDecimal.valueOf(182.94));
        ExpectedEachProduct eachProduct3SS6 = new ExpectedEachProduct(BigDecimal.valueOf(225.51), BigDecimal.valueOf(225.51));
        List<ExpectedEachProduct> expectedEachProductsSS6 = List.of(eachProduct1SS6, eachProduct2SS6, eachProduct3SS6);
        TestData ss6Data = TestData.create(List.of(product1, product2, product1), expectedEachProductsSS6, BigDecimal.valueOf(225.51), BigDecimal.valueOf(225.51));
        Named<TestData> ss6 = Named.of("SS-6 cart with multiple items and mixed of qty", ss6Data);

        ExpectedEachProduct eachProduct1SS11 = new ExpectedEachProduct(BigDecimal.valueOf(42.57), BigDecimal.valueOf(42.57));
        ExpectedEachProduct eachProduct2SS11 = new ExpectedEachProduct(BigDecimal.valueOf(182.94), BigDecimal.valueOf(182.94));
        List<ExpectedEachProduct> expectedEachProductsSS11 = List.of(eachProduct1SS11, eachProduct2SS11);
        TestData ss11Data = TestData.create(List.of(product1, product2), expectedEachProductsSS11, BigDecimal.valueOf(182.94), BigDecimal.valueOf(182.94));
        Named<TestData> ss11 = Named.of("SS-11 cart add items in sequence and check price after each step", ss11Data);

        ExpectedEachProduct eachProduct1SS12 = new ExpectedEachProduct(BigDecimal.valueOf(140.37), BigDecimal.valueOf(120.37));
        ExpectedEachProduct eachProduct2SS12 = new ExpectedEachProduct(BigDecimal.valueOf(182.94), BigDecimal.valueOf(162.94));
        List<ExpectedEachProduct> expectedEachProductsSS12 = List.of(eachProduct1SS12, eachProduct2SS12);
        TestData ss12Data = TestData.create(List.of(product2, product1), expectedEachProductsSS12, BigDecimal.valueOf(182.94), BigDecimal.valueOf(162.94));
        ss12Data.coupon = coupon;
        Named<TestData> ss12 = Named.of("SS-12 cart add items along with applying coupon and then add another item", ss12Data);

        return Stream.of(
                Arguments.of(ss2),
                Arguments.of(ss3),
                Arguments.of(ss4),
                Arguments.of(ss5),
                Arguments.of(ss6),
                Arguments.of(ss11),
                Arguments.of(ss12)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCartTestCases")
    void testAddProduct(TestData data) {
        Cart cart = Cart.create(generatedCartId);

        if (!data.expectedProducts.isEmpty()) {
            for (int i = 0; i < data.expectedProducts.size(); i++) {
                cart.addProduct(data.expectedProducts.get(i));

                if (data.coupon != null) {
                    data.coupon.applyToCart(cart);
                }

                MatcherAssert.assertThat(cart.getSubtotal(), Matchers.comparesEqualTo(data.expectedEachProducts.get(i).expectedEachSubTotal));
                MatcherAssert.assertThat(cart.getTotal(), Matchers.comparesEqualTo(data.expectedEachProducts.get(i).expectedEachTotal));
            }
        }

        MatcherAssert.assertThat(cart.getId(), CoreMatchers.equalTo(generatedCartId));
        MatcherAssert.assertThat(cart.getNumberOfItems(), CoreMatchers.equalTo(data.expectedProducts.size()));
        MatcherAssert.assertThat(cart.getSubtotal(), Matchers.comparesEqualTo(data.expectedSubTotal));
        MatcherAssert.assertThat(cart.getTotal(), Matchers.comparesEqualTo(data.expectedTotal));
    }
}