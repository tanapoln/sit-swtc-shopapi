package com.sit.shopping.cart.repository;

import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.exception.EntityNotFoundException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class CartRepositoryInMemTest {
    private CartRepositoryInMem underTest;

    @BeforeEach
    void setUp() {
        underTest = new CartRepositoryInMem();
    }

    @Test
    void testCreateCart() {
        String cartId = "cart-mock-001";

        Cart cart = underTest.createCart(cartId);

        MatcherAssert.assertThat(cart.getId(), CoreMatchers.equalTo(cartId));
        MatcherAssert.assertThat(cart.getLineItems().size(), Matchers.comparesEqualTo(0));
        MatcherAssert.assertThat(cart.getDiscountAmount(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        MatcherAssert.assertThat(cart.getSubtotal(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        MatcherAssert.assertThat(cart.getTotal(), Matchers.comparesEqualTo(BigDecimal.ZERO));
    }

    @Test
    void testSaveCart() {
        Cart cart = new Cart();

        Assertions.assertDoesNotThrow(() -> {
            underTest.save(cart);
        });
    }

    @Test
    void testSaveNullCart() {

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            underTest.save(null);
        });
    }

    @Test
    void testSaveNullCartId() {
        Cart cart = new Cart();
        cart.setId(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            underTest.save(cart);
        });
    }

    @Test
    void testFindByCartId() {
        Cart expected = underTest.createCart("mock-create-expected-id-1");

        Cart actualCart = underTest.findByCartId(expected.getId());

        MatcherAssert.assertThat(actualCart.getId(), CoreMatchers.equalTo(expected.getId()));
    }

    @Test
    void testFindByCartIdNotFound() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            underTest.findByCartId("invalidCaRtId-x-1-INSERT INTO $");
        });
    }

    @Test
    void testFindByNullCartIdNotFound() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            underTest.findByCartId(null);
        });
    }
}