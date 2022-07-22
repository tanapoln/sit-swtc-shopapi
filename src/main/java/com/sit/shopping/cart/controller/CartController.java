package com.sit.shopping.cart.controller;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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
import com.sit.shopping.cart.service.CartService;
import com.sit.shopping.exception.InvalidCouponException;

@RestController
@RequestMapping("/cart")
@Validated
public class CartController {
	@Autowired
	private CartService cartService;

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping("/add")
	public AddProductResponse addProductToCart(@RequestBody @Valid AddProductRequest request) {
		Cart cart = cartService.addProductToCart(request.getCartId(), request.getProductId());
		return new AddProductResponse(cart);
	}

	@GetMapping("/status")
	public CartStatusDTO getCartStatus(@RequestParam @Valid @NotBlank String cartId) {
		Cart cart = cartService.getCart(cartId);

		return new CartStatusDTO(cart);
	}

	@GetMapping("/summary")
	public Cart getCartSummary(@RequestParam String cartId) {
		Cart cart = cartService.getCart(cartId);
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
