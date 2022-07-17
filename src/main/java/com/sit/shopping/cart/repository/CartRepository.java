package com.sit.shopping.cart.repository;

import com.sit.shopping.cart.model.Cart;

public interface CartRepository {
    Cart createCart();

    Cart findByCartId(String cartId);
}
