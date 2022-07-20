package com.sit.shopping.cart.controller;

import com.sit.shopping.cart.dto.*;
import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.cart.service.CartService;
import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.exception.InvalidCouponException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;

class CartControllerTest {
	private CartController underTest;

	private CartService mockCartService;

	@BeforeEach
	void setUp() {
		underTest = new CartController();

		mockCartService = Mockito.mock(CartService.class);
		underTest.setCartService(mockCartService);
	}

	@Test
	void testAddToCart() {
		AddProductRequest request = new AddProductRequest();

		Cart expectedCart = new Cart(request.getCartId());
		expectedCart.setNumberOfItems(1);

		Mockito.when(mockCartService.addProductToCart(request.getCartId(), request.getProductId())).thenReturn(expectedCart);

		AddProductResponse response = underTest.addProductToCart(request);

		MatcherAssert.assertThat(response.getCartId(), CoreMatchers.equalTo(request.getCartId()));
		MatcherAssert.assertThat(response.getNumberOfItems(), CoreMatchers.equalTo(1));
	}

	@Test
	void testAddToCartButProductNotFound() {
		AddProductRequest request = new AddProductRequest();

		Mockito.when(mockCartService.addProductToCart(request.getCartId(), request.getProductId()))
				.thenThrow(EntityNotFoundException.class);

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			underTest.addProductToCart(request);
		});
	}

	@Test
	void testGetCartStatus() {
		String cartId = "cart-mock";

		Cart expectedCart = new Cart(cartId);
		expectedCart.setNumberOfItems(1);

		Mockito.when(mockCartService.getCart(cartId)).thenReturn(expectedCart);

		CartStatusDTO response = underTest.getCartStatus(cartId);

		MatcherAssert.assertThat(response.getNumberOfItems(), CoreMatchers.equalTo(1));
	}

	@Test
	void testGetCartSummary() {
		String cartId = "cart-mock";

		Cart expectedCart = new Cart(cartId);
		expectedCart.setNumberOfItems(1);

		Mockito.when(mockCartService.getCart(cartId)).thenReturn(expectedCart);

		Cart response = underTest.getCartSummary(cartId);

		MatcherAssert.assertThat(response.getId(), CoreMatchers.equalTo(cartId));
		MatcherAssert.assertThat(response.getSubtotal(), CoreMatchers.equalTo(expectedCart.getSubtotal()));
		MatcherAssert.assertThat(response.getTotal(), CoreMatchers.equalTo(expectedCart.getTotal()));
		MatcherAssert.assertThat(response.getDiscountAmount(), CoreMatchers.equalTo(expectedCart.getDiscountAmount()));
		MatcherAssert.assertThat(response.getNumberOfItems(), CoreMatchers.equalTo(1));
	}

    @Test
    void testApplyCouponSuccess() {
        String couponCode = "TGIF20-1";
        String cartId = "CART-001";

        ApplyCouponRequest request = new ApplyCouponRequest();
        request.setCoupon(couponCode);
        request.setCartId(cartId);

        Cart expectedCart = new Cart();
        expectedCart.setDiscountAmount(BigDecimal.valueOf(20.0));
        expectedCart.setDiscountDescription("Discount 20$");

        Mockito.when(mockCartService.applyCoupon(cartId, couponCode)).thenReturn(expectedCart);

        ApplyCouponResponse response = underTest.applyCoupon(request);

        MatcherAssert.assertThat(response.getDescription(), CoreMatchers.equalTo("Discount 20$"));
        MatcherAssert.assertThat(response.isSuccess(), CoreMatchers.equalTo(true));
    }

    @Test
    void testApplyCouponFailed() {
        String couponCode = "TGIF20-1";
        String cartId = "CART-001";

        ApplyCouponRequest request = new ApplyCouponRequest();
        request.setCoupon(couponCode);
        request.setCartId(cartId);

        Cart expectedCart = new Cart();
        expectedCart.setDiscountAmount(BigDecimal.ZERO);
        expectedCart.setDiscountDescription(null);

        Mockito.when(mockCartService.applyCoupon(cartId, couponCode)).thenReturn(expectedCart);

        Assertions.assertThrows(InvalidCouponException.class, () -> {
            underTest.applyCoupon(request);
        });
    }

    @Test
    void testApplyCouponButNotFound() {
        String couponCode = "invalid-coupon";
        String cartId = "cart-001-invalid";

        ApplyCouponRequest request = new ApplyCouponRequest();
        request.setCoupon(couponCode);
        request.setCartId(cartId);

        Mockito.when(mockCartService.applyCoupon(cartId, couponCode)).thenThrow(InvalidCouponException.class);

        Assertions.assertThrows(InvalidCouponException.class, () -> {
            underTest.applyCoupon(request);
        });
    }
}