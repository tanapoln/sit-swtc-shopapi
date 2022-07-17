package com.sit.shopping.cart.dto;

import com.sit.shopping.cart.model.Cart;

public class AddProductResponse {
    private String cartId;
    private Integer numberOfItems;

    public AddProductResponse(Cart cart) {
        this.cartId = cart.getId();
        this.numberOfItems = cart.getProducts().size();
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}
