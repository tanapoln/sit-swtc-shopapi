package com.sit.shopping.cart.service;

import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.cart.model.CartItem;
import com.sit.shopping.cart.repository.CartRepository;
import com.sit.shopping.coupon.model.Coupon;
import com.sit.shopping.coupon.repository.CouponRepository;
import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

class CartServiceImplTest {
    private CartServiceImpl underTest;

    private ProductRepository mockProductRepository;
    private CartRepository mockCartRepository;
    private CouponRepository mockCouponRepository;

    @BeforeEach
    void setUp() {
        underTest = new CartServiceImpl();

        mockProductRepository = Mockito.mock(ProductRepository.class);
        underTest.setProductRepository(mockProductRepository);

        mockCartRepository = Mockito.mock(CartRepository.class);
        underTest.setCartRepository(mockCartRepository);

        mockCouponRepository = Mockito.mock(CouponRepository.class);
        underTest.setCouponRepository(mockCouponRepository);
    }

    @Test
    void testAddProductToNewCart() {
        String cartId = "cart-id";
        String productId = "product-id";

        Product shirt = new Product("T-Shirt", 10.0, "");
        Mockito.when(mockProductRepository.findByProductId(productId)).thenReturn(shirt);

        Mockito.when(mockCartRepository.findByCartId(cartId)).thenThrow(EntityNotFoundException.class);

        Cart expectedCart = new Cart(cartId);
        Mockito.when(mockCartRepository.createCart(cartId)).thenReturn(expectedCart);

        Cart cart = underTest.addProductToCart(cartId, productId);

        MatcherAssert.assertThat(cart.getId(), CoreMatchers.equalTo(cartId));
        MatcherAssert.assertThat(cart.getSubtotal(), Matchers.comparesEqualTo(BigDecimal.valueOf(10)));
        MatcherAssert.assertThat(cart.getTotal(), Matchers.comparesEqualTo(BigDecimal.valueOf(10)));
        MatcherAssert.assertThat(cart.getDiscountAmount(), Matchers.comparesEqualTo(BigDecimal.valueOf(0)));
        MatcherAssert.assertThat(cart.getNumberOfItems(), Matchers.comparesEqualTo(1));
        MatcherAssert.assertThat(cart.getLineItems().stream().findFirst().get().getQuantity(), Matchers.comparesEqualTo(1));
    }

    @Test
    void testAddDuplicatedProductToCart() {
        String cartId = "cart-id";
        String productId = "product-id";

        Product shirt = new Product("T-Shirt", 10.0, "");
        shirt.setId(productId);
        Mockito.when(mockProductRepository.findByProductId(productId)).thenReturn(shirt);

        Cart expectedCart = new Cart(cartId);
        expectedCart.getLineItems().add(new CartItem("product-id", "T-Shirt", 1, BigDecimal.TEN));
        expectedCart.setNumberOfItems(1);
        Mockito.when(mockCartRepository.findByCartId(cartId)).thenReturn(expectedCart);

        Cart cart = underTest.addProductToCart(cartId, productId);

        MatcherAssert.assertThat(cart.getId(), CoreMatchers.equalTo(cartId));
        MatcherAssert.assertThat(cart.getSubtotal(), Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        MatcherAssert.assertThat(cart.getTotal(), Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        MatcherAssert.assertThat(cart.getDiscountAmount(), Matchers.comparesEqualTo(BigDecimal.valueOf(0)));
        MatcherAssert.assertThat(cart.getNumberOfItems(), Matchers.comparesEqualTo(2));
        MatcherAssert.assertThat(cart.getLineItems().stream().findFirst().get().getQuantity(), Matchers.comparesEqualTo(2));
    }

    @Test
    void testAddNewProductToCreatedCart() {
        String cartId = "cart-id";
        String productId = "product-id";

        Product shirt = new Product("T-Shirt", 10.0, "");
        Mockito.when(mockProductRepository.findByProductId(productId)).thenReturn(shirt);

        Cart expectedCart = new Cart(cartId);
        expectedCart.getLineItems().add(new CartItem("product-id-2", "Plant", 1, BigDecimal.TEN));
        expectedCart.setNumberOfItems(1);
        Mockito.when(mockCartRepository.findByCartId(cartId)).thenReturn(expectedCart);

        Cart cart = underTest.addProductToCart(cartId, productId);

        MatcherAssert.assertThat(cart.getId(), CoreMatchers.equalTo(cartId));
        MatcherAssert.assertThat(cart.getSubtotal(), Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        MatcherAssert.assertThat(cart.getTotal(), Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        MatcherAssert.assertThat(cart.getDiscountAmount(), Matchers.comparesEqualTo(BigDecimal.valueOf(0)));
        MatcherAssert.assertThat(cart.getNumberOfItems(), Matchers.comparesEqualTo(2));
        MatcherAssert.assertThat(cart.getLineItems().size(), Matchers.comparesEqualTo(2));
        for (CartItem lineItem : cart.getLineItems()) {
            MatcherAssert.assertThat(lineItem.getUnitPrice(), Matchers.comparesEqualTo(BigDecimal.TEN));
            MatcherAssert.assertThat(lineItem.getQuantity(), CoreMatchers.equalTo(1));
        }
    }

    @Test
    void testApplyCouponSuccess() {
        String cartId = "cart-id";
        String couponCode = "coupon-code-id";

        Cart expectedCart = new Cart(cartId);
        expectedCart.getLineItems().add(new CartItem("product-id-2", "Plant", 1, BigDecimal.valueOf(30)));
        expectedCart.setSubtotal(BigDecimal.valueOf(30));
        expectedCart.setTotal(BigDecimal.valueOf(30));
        Mockito.when(mockCartRepository.findByCartId(cartId)).thenReturn(expectedCart);

        Coupon coupon = new Coupon(couponCode, "20$", "Get Free 20$", "FIXED_AMOUNT", BigDecimal.valueOf(20), BigDecimal.valueOf(10));
        Mockito.when(mockCouponRepository.findByCoupon(couponCode)).thenReturn(coupon);

        Cart cart = underTest.applyCoupon(cartId, couponCode);

        MatcherAssert.assertThat(cart.getDiscountAmount(), Matchers.comparesEqualTo(coupon.getDiscountAmount()));
        MatcherAssert.assertThat(cart.getDiscountDescription(), CoreMatchers.equalTo("Get Free 20$"));
        MatcherAssert.assertThat(cart.getSubtotal(), Matchers.comparesEqualTo(BigDecimal.valueOf(30)));
        MatcherAssert.assertThat(cart.getTotal(), Matchers.comparesEqualTo(BigDecimal.TEN));
    }

    @Test
    void testApplyCouponFailedByUnderMinimumAmount() {
        String cartId = "cart-id";
        String couponCode = "coupon-code-id";

        Cart expectedCart = new Cart(cartId);
        expectedCart.getLineItems().add(new CartItem("product-id-2", "Plant", 1, BigDecimal.valueOf(10)));
        expectedCart.setSubtotal(BigDecimal.valueOf(10));
        expectedCart.setTotal(BigDecimal.valueOf(10));
        Mockito.when(mockCartRepository.findByCartId(cartId)).thenReturn(expectedCart);

        Coupon coupon = new Coupon(couponCode, "20$", "Get Free 20$ Minimum 20$", "FIXED_AMOUNT", BigDecimal.valueOf(20), BigDecimal.valueOf(20));
        Mockito.when(mockCouponRepository.findByCoupon(couponCode)).thenReturn(coupon);

        Cart cart = underTest.applyCoupon(cartId, couponCode);

        MatcherAssert.assertThat(cart.getDiscountAmount(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        MatcherAssert.assertThat(cart.getDiscountDescription(), CoreMatchers.equalTo(null));
        MatcherAssert.assertThat(cart.getSubtotal(), Matchers.comparesEqualTo(BigDecimal.TEN));
        MatcherAssert.assertThat(cart.getTotal(), Matchers.comparesEqualTo(BigDecimal.TEN));
    }
}