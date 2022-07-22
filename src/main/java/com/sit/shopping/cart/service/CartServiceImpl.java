package com.sit.shopping.cart.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.cart.model.CartItem;
import com.sit.shopping.cart.repository.CartRepository;
import com.sit.shopping.coupon.model.Coupon;
import com.sit.shopping.coupon.repository.CouponRepository;
import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CouponRepository couponRepository;

	@Override
	public Cart addProductToCart(String cartId, String productId) {
		Product product = productRepository.findByProductId(productId);

		Cart cart = findOrCreateCart(cartId);

		List<CartItem> lineItems = cart.getLineItems();

		Optional<CartItem> existingItem = lineItems.stream().filter(cartItem -> cartItem.getId().equals(product.getId()))
				.findFirst();

		if (existingItem.isEmpty()) {
			lineItems.add(new CartItem(product.getId(), product.getName(), 1, new BigDecimal(product.getPrice())));
		} else {
			CartItem cartItem = existingItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + 1);
		}

		calculate(cart);

		cartRepository.save(cart);

		return cart;
	}

	@Override
	public Cart getCart(String cartId) {
		return findOrCreateCart(cartId);
	}

	private Cart findOrCreateCart(String cartId) {
		try {
			return cartRepository.findByCartId(cartId);
		} catch (EntityNotFoundException e) {
			return cartRepository.createCart(cartId);
		}
	}

	@Override
	public Cart applyCoupon(String cartId, String couponCode) {
		Cart cart = cartRepository.findByCartId(cartId);

		if (!StringUtils.hasText(couponCode)) {
			cart.removeCoupon();
			return cart;
		}

		Coupon coupon = couponRepository.findByCoupon(couponCode);

		if (cart.getSubtotal().compareTo(coupon.getMinimumAmount()) < 0) {
			cart.removeCoupon();
		} else {
			cart.applyCoupon(coupon);
		}

		calculate(cart);

		cartRepository.save(cart);

		return cart;
	}

	private void calculate(Cart cart) {
		int numberOfItems = cart.getLineItems().stream().mapToInt(value -> value.getQuantity()).sum();
		cart.setNumberOfItems(numberOfItems);

		BigDecimal subtotal = BigDecimal.ZERO;
		for (CartItem item : cart.getLineItems()) {
			subtotal = subtotal.add(item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
		}
		cart.setSubtotal(subtotal);

		BigDecimal total = subtotal.subtract(cart.getDiscountAmount());
		cart.setTotal(total);
	}
}
