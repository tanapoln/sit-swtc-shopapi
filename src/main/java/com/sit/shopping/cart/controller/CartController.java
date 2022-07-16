package com.sit.shopping.cart.controller;

import com.sit.shopping.cart.dto.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @PostMapping("/add")
    public AddProductResponse addProductToCart(@RequestBody(required = true) AddProductRequest request) {
        return new AddProductResponse();
    }

    @GetMapping("/status")
    public CartStatusDTO getCartStatus(@RequestParam(required = true) String cartId) {
        return new CartStatusDTO();
    }

    @GetMapping("/summary")
    public CartSummaryDTO getCartSummary(@RequestParam(required = true) String cartId) {
        return new CartSummaryDTO();
    }

    @PostMapping("/applyCoupon")
    public ApplyCouponResponse applyCoupon(@RequestBody(required = true) ApplyCouponRequest request) {
        return new ApplyCouponResponse();
    }
}
