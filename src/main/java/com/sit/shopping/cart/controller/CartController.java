package com.sit.shopping.cart.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.sit.shopping.cart.repository.CartRepository;
import com.sit.shopping.coupon.model.Coupon;
import com.sit.shopping.coupon.repository.CouponRepository;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sit.shopping.cart.dto.AddProductRequest;
import com.sit.shopping.cart.dto.AddProductResponse;
import com.sit.shopping.cart.dto.ApplyCouponRequest;
import com.sit.shopping.cart.dto.ApplyCouponResponse;
import com.sit.shopping.cart.dto.CartStatusDTO;
import com.sit.shopping.cart.model.Cart;

@RestController
@RequestMapping("/cart")
@Validated
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;

    @PostMapping("/add")
    public AddProductResponse addProductToCart(@RequestBody @Valid AddProductRequest request) {
        Product product = productRepository.findByProductId(request.getProductId());

        Cart cart = cartRepository.findOrCreateCart(request.getCartId());

        cart.addProduct(product);

        cartRepository.save(cart);

        return new AddProductResponse(cart);
    }

    @GetMapping("/status")
    public CartStatusDTO getCartStatus(@RequestParam @Valid @NotBlank String cartId) {
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
        Cart cart = cartRepository.findOrCreateCart(request.getCartId());

        Coupon coupon = couponRepository.findByCoupon(request.getCoupon());

        coupon.applyToCart(cart);

        cartRepository.save(cart);

        return new ApplyCouponResponse(cart.getDiscountDescription());
    }
}
