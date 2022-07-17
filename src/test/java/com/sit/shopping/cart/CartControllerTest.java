package com.sit.shopping.cart;

import com.sit.shopping.cart.controller.CartController;
import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.cart.repository.CartRepositoryInMem;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartControllerTest {
    private CartController underTest;
    private CartRepositoryInMem cartRepository;

    @BeforeEach
    void setUp() {
        underTest  = new CartController();

        cartRepository  = new CartRepositoryInMem();

        underTest.setCartRepository(cartRepository);
    }

    @Test
    void testGetCartSummary() {
        String cartId = "ce0b9fbe-7ad8-11eb-9439-0242ac130002";

        Cart cartSummary = underTest.getCartSummary(cartId);

        MatcherAssert.assertThat(cartSummary.getLineItems().size(), CoreMatchers.equalTo(0));
    }
}