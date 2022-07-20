package com.sit.shopping.cart.controller;

import com.sit.shopping.cart.dto.AddProductRequest;
import com.sit.shopping.cart.dto.AddProductResponse;
import com.sit.shopping.cart.dto.CartStatusDTO;
import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.cart.repository.CartRepository;
import com.sit.shopping.cart.service.CartService;
import com.sit.shopping.exception.EntityNotFoundException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class CartControllerTest {
    private CartController underTest;

    private CartService mockCartService;
    private CartRepository mockCartRepository;

    @BeforeEach
    void setUp() {
        underTest = new CartController();

        mockCartService = Mockito.mock(CartService.class);
        underTest.setCartService(mockCartService);

        mockCartRepository = Mockito.mock(CartRepository.class);
        underTest.setCartRepository(mockCartRepository);
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
    void testAddToCartButEntityNotFound() {
        AddProductRequest request = new AddProductRequest();

        Mockito.when(mockCartService.addProductToCart(request.getCartId(), request.getProductId())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            underTest.addProductToCart(request);
        });
    }

    @Test
    void testGetCartStatus() {
        String cartId = "cart-mock";

        Cart expectedCart = new Cart(cartId);
        expectedCart.setNumberOfItems(1);

        Mockito.when(mockCartRepository.findByCartId(cartId)).thenReturn(expectedCart);

        CartStatusDTO response = underTest.getCartStatus(cartId);

        MatcherAssert.assertThat(response.getNumberOfItems(), CoreMatchers.equalTo(1));
    }

    @Test
    void testGetCartStatusButNotFound() {
        String cartId = "cart-mock";

        Mockito.when(mockCartRepository.findByCartId(cartId)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            underTest.getCartStatus(cartId);
        });
    }

    @Test
    void testGetCartSummary() {
        String cartId = "cart-mock";

        Cart expectedCart = new Cart(cartId);
        expectedCart.setNumberOfItems(1);

        Mockito.when(mockCartRepository.findByCartId(cartId)).thenReturn(expectedCart);

        Cart response = underTest.getCartSummary(cartId);

        MatcherAssert.assertThat(response.getId(), CoreMatchers.equalTo(cartId));
        MatcherAssert.assertThat(response.getSubtotal(), CoreMatchers.equalTo(expectedCart.getSubtotal()));
        MatcherAssert.assertThat(response.getTotal(), CoreMatchers.equalTo(expectedCart.getTotal()));
        MatcherAssert.assertThat(response.getDiscountAmount(), CoreMatchers.equalTo(expectedCart.getDiscountAmount()));
        MatcherAssert.assertThat(response.getNumberOfItems(), CoreMatchers.equalTo(1));
    }

    @Test
    void testGetCartSummaryButNotFound() {
        String cartId = "cart-mock";

        Mockito.when(mockCartRepository.findByCartId(cartId)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            underTest.getCartSummary(cartId);
        });
    }
}