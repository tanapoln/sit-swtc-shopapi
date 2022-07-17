package com.sit.shopping.cart.dto;

import com.sit.shopping.cart.model.Cart;

public class CartStatusDTO {
    private Integer numberOfItems;

    public CartStatusDTO(Cart cart) {
        this.numberOfItems = cart.getNumberOfItems();
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}
