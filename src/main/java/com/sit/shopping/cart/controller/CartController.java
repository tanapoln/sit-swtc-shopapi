package com.sit.shopping.cart.controller;

import com.sit.shopping.cart.dto.*;
import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.cart.repository.CartRepository;
import com.sit.shopping.cart.service.CartService;
import com.sit.shopping.exception.ErrorResponse;
import com.sit.shopping.exception.InvalidCouponException;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @PostMapping("/add")
    public AddProductResponse addProductToCart(@RequestBody @Valid AddProductRequest request) {
        Cart cart = cartService.addProductToCart(request.getCartId(), request.getProductId());
        return new AddProductResponse(cart);
    }

    @GetMapping("/status")
    public CartStatusDTO getCartStatus(@RequestParam String cartId) {
        Cart cart = cartRepository.findByCartId(cartId);

        return new CartStatusDTO(cart);
    }

    @GetMapping("/summary")
    public Cart getCartSummary(@RequestParam String cartId) {
        Cart cart = cartRepository.findByCartId(cartId);
        return cart;
    }

    @PostMapping("/applyCoupon")
    public ApplyCouponResponse applyCoupon(@RequestBody @Valid ApplyCouponRequest request) {
        Cart cart = cartService.applyCoupon(request.getCartId(), request.getCoupon());

        boolean isAppliedSuccess = cart.getDiscountAmount() != null && cart.getDiscountAmount().compareTo(BigDecimal.ZERO) > 0;

        if (!isAppliedSuccess) {
            throw new InvalidCouponException();
        }

        return new ApplyCouponResponse(cart.getDiscountDescription());
    }
}
