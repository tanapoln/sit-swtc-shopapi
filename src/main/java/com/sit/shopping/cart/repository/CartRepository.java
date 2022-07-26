package com.sit.shopping.cart.repository;

import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.exception.EntityNotFoundException;

public interface CartRepository {
    Cart createCart(String cartId);

    Cart findByCartId(String cartId);

    Cart findOrCreateCart(String cartId);

    void save(Cart cart);
}
