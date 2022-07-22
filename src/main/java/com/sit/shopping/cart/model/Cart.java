package com.sit.shopping.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sit.shopping.coupon.model.Coupon;

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

    @JsonIgnoreProperties
    private String discountDescription;
    @JsonIgnoreProperties
    private int numberOfItems;

    public Cart(String id) {
        this.id = id;
        this.lineItems = new ArrayList<>();
        this.subtotal = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
        this.discountAmount = BigDecimal.ZERO;
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
        this.subtotal = subtotal.setScale(2);
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount.setScale(2);
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
        this.total = total.setScale(2);
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public void applyCoupon(Coupon coupon) {
        if (coupon == null) {
            this.setDiscountAmount(BigDecimal.ZERO);
            this.setDiscountName(null);
            this.setDiscountDescription(null);
        } else {
            this.setDiscountAmount(coupon.getDiscountAmount());
            this.setDiscountName(coupon.getName());
            this.setDiscountDescription(coupon.getDescription());
        }
    }

    public void removeCoupon() {
        this.applyCoupon(null);
    }
}
