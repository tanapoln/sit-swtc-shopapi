package com.sit.shopping.cart.service;

import com.sit.shopping.cart.model.Cart;

public interface CartService {
    Cart addProductToCart(String cartId, String productId);

    Cart getCart(String cartId);

    Cart applyCoupon(String cartId, String coupon);
}
