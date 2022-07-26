package com.sit.shopping.cart.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartItem {
    private String id;
    private String name;
    private int quantity;
    private BigDecimal unitPrice;

    public static CartItem create(String id, String name, int quantity, BigDecimal unitPrice) {
        CartItem cartItem = new CartItem();
        cartItem.id = id;
        cartItem.name = name;
        cartItem.quantity = quantity;
        cartItem.unitPrice = unitPrice.setScale(2, RoundingMode.HALF_UP);
        return cartItem;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
