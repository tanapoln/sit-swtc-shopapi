package com.sit.shopping.cart.service;

import com.sit.shopping.cart.model.Cart;
import com.sit.shopping.cart.model.CartItem;
import com.sit.shopping.cart.repository.CartRepository;
import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addProductToCart(String cartId, String productId) {
        Product product = productRepository.findByProductId(productId);

        Cart cart;
        try {
            cart = cartRepository.findByCartId(cartId);
        } catch (EntityNotFoundException e) {
            cart = cartRepository.createCart();
        }

        List<CartItem> lineItems = cart.getLineItems();

        Optional<CartItem> existingItem = lineItems.stream().filter(cartItem -> cartItem.getId().equals(product.getId())).findFirst();

        if (existingItem.isEmpty()) {
            lineItems.add(new CartItem(product.getId(), product.getName(), 1, new BigDecimal(product.getPrice())));
        } else {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        return cart;
    }
}
