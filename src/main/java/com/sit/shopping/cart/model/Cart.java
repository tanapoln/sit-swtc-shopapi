package com.sit.shopping.cart.model;

import com.sit.shopping.product.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String id;
    private List<CartItem> lineItems;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private String discountName;
    private BigDecimal total;

    public Cart(String id) {
        this.id = id;
        this.lineItems = new ArrayList<>();
    }

    public Cart() {
        this("generated-c-" + System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CartItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<CartItem> lineItems) {
        this.lineItems = lineItems;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getNumberOfItems() {
        if (lineItems == null || lineItems.isEmpty()) {
            return 0;
        }

        return lineItems.stream().mapToInt(value -> value.getQuantity()).sum();
    }
}
