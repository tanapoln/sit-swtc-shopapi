package com.sit.shopping.cart.repository;

import com.sit.shopping.cart.model.Cart;

public interface CartRepository {
    Cart createCart(String cartId);

    Cart findByCartId(String cartId);

    void save(Cart cart);
}
