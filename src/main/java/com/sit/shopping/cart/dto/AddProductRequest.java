package com.sit.shopping.cart.dto;

import javax.validation.constraints.NotNull;

public class AddProductRequest {
    @NotNull
    private String cartId;
    @NotNull
    private String productId;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
