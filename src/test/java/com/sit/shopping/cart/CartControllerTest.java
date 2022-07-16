package com.sit.shopping.cart;

import com.sit.shopping.cart.controller.CartController;
import com.sit.shopping.cart.dto.CartSummaryDTO;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartControllerTest {
    private CartController underTest;

    @BeforeEach
    void setUp() {
        underTest  = new CartController();
    }

    @Test
    void testGetCartSummary() {
        String cartId = "cartId";

        CartSummaryDTO cartSummary = underTest.getCartSummary(cartId);

        MatcherAssert.assertThat(cartSummary.getLineItems().size(), CoreMatchers.equalTo(1));
    }
}