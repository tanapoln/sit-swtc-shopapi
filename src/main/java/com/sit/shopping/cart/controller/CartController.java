package com.sit.shopping.cart.controller;

import com.sit.shopping.cart.dto.*;
import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.cart.repository.CartRepository;
import com.sit.shopping.cart.service.CartService;
import com.sit.shopping.exception.ErrorResponse;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/add")
    public AddProductResponse addProductToCart(@RequestBody(required = true) AddProductRequest request) {
        Cart cart = cartService.addProductToCart(request.getCartId(), request.getProductId());
        return new AddProductResponse(cart);
    }

    @GetMapping("/status")
    public CartStatusDTO getCartStatus(@RequestParam(required = true) String cartId) {
        Cart cart = cartRepository.findByCartId(cartId);

        return new CartStatusDTO(cart);
    }

    @GetMapping("/summary")
    public Cart getCartSummary(@RequestParam(required = true) String cartId) {
        Cart cart = cartRepository.findByCartId(cartId);
        return cart;
    }

    @PostMapping("/applyCoupon")
    public ResponseEntity applyCoupon(@RequestBody(required = true) ApplyCouponRequest request) {
        Cart cart = cartService.applyCoupon(request.getCartId(), request.getCoupon());

        boolean isAppliedSuccess = cart.getDiscountAmount() != null && cart.getDiscountAmount().compareTo(BigDecimal.ZERO) > 0;

        if (!isAppliedSuccess) {
            ErrorResponse errorResponse = new ErrorResponse("Coupon cannot be applied due to expired or invalid", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApplyCouponResponse(cart.getDiscountDescription()), HttpStatus.OK);
    }
}
