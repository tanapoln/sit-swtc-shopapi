package com.sit.shopping.cart.dto;

import java.util.List;

public class CartSummaryDTO {
    private List<LineItemDTO> lineItems;
    private Double subtotal;
    private Double discountAmount;
    private String discountName;
    private Double total;

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<LineItemDTO> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemDTO> lineItems) {
        this.lineItems = lineItems;
    }
}
