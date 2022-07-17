package com.sit.shopping.cart.repository;

import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class CartRepositoryInMem implements CartRepository {
    private HashMap<String, Cart> cartMap;

    public CartRepositoryInMem() {
        if (cartMap == null) {
            cartMap = new HashMap<>();
        }
        cartMap.putIfAbsent("ce0b9fbe-7ad8-11eb-9439-0242ac130002", new Cart("ce0b9fbe-7ad8-11eb-9439-0242ac130002"));
    }

    @Override
    public Cart createCart() {
        Cart cart = new Cart();

        cartMap.put(cart.getId(), cart);

        return cart;
    }

    @Override
    public Cart findByCartId(String cartId) {
        Cart cart = cartMap.get(cartId);

        if (cart == null) {
            throw new EntityNotFoundException("Cart cannot be found");
        }

        return cart;
    }

    @Override
    public void save(Cart cart) {
        cartMap.put(cart.getId(), cart);
    }
}
