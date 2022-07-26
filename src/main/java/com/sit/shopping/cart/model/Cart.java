package com.sit.shopping.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sit.shopping.coupon.model.Coupon;
import com.sit.shopping.product.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Cart {
    private String id;

    private List<CartItem> lineItems;
    @JsonIgnoreProperties
    private int numberOfItems;

    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal total;

    private String discountName;
    @JsonIgnoreProperties
    private String discountDescription;

    public static Cart create(String id) {
        Cart cart = new Cart();
        cart.id = id;
        cart.lineItems = new ArrayList<>();
        cart.subtotal = BigDecimal.ZERO;
        cart.total = BigDecimal.ZERO;
        cart.discountAmount = BigDecimal.ZERO;
        return cart;
    }

    private Cart() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public List<CartItem> getLineItems() {
        return lineItems;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public String getDiscountName() {
        return discountName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    private void calculate() {
        BigDecimal newSubTotal = BigDecimal.ZERO;
        for (CartItem item : lineItems) {
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity()).setScale(2);
            newSubTotal = newSubTotal.add(item.getUnitPrice().multiply(quantity));
        }

        subtotal = newSubTotal.setScale(2, RoundingMode.HALF_UP);
        total = subtotal.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }

    private void updateNumberOfItems() {
        numberOfItems = lineItems.stream().mapToInt(value -> value.getQuantity()).sum();
    }

    public void applyCoupon(Coupon coupon) {
        this.discountAmount = coupon.getDiscountAmount();
        this.discountName = coupon.getName();
        this.discountDescription = coupon.getDescription();

        calculate();
    }

    public void removeCoupon() {
        this.discountAmount = BigDecimal.ZERO;
        this.discountName = null;
        this.discountDescription = null;

        calculate();
    }

    public void addProduct(Product product) {
        Optional<CartItem> existingItem = lineItems.stream().filter(cartItem -> cartItem.getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isEmpty()) {
            CartItem cartItem = CartItem.create(product.getId(), product.getName(), 1, new BigDecimal(product.getPrice()));
            lineItems.add(cartItem);
        } else {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        calculate();

        updateNumberOfItems();
    }
}
