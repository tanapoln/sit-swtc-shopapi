package com.sit.shopping.cart.controller;

import com.sit.shopping.cart.dto.*;
import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.cart.service.CartService;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public AddProductResponse addProductToCart(@RequestBody(required = true) AddProductRequest request) {
        Cart cart = cartService.addProductToCart(request.getCartId(), request.getProductId());
        return new AddProductResponse(cart);
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
