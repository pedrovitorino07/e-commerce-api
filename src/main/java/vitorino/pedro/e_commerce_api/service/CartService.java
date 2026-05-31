package vitorino.pedro.e_commerce_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.dto.CartItemResponseDTO;
import vitorino.pedro.e_commerce_api.dto.CartResponseDTO;
import vitorino.pedro.e_commerce_api.entity.Cart;
import vitorino.pedro.e_commerce_api.entity.CartItem;
import vitorino.pedro.e_commerce_api.entity.Product;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.exception.InsufficientStockException;
import vitorino.pedro.e_commerce_api.exception.ProductNotFoundException;
import vitorino.pedro.e_commerce_api.exception.UserNotFoundException;
import vitorino.pedro.e_commerce_api.repository.CartRepository;
import vitorino.pedro.e_commerce_api.repository.ProductRepository;
import vitorino.pedro.e_commerce_api.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not authenticated"));
    }

    private CartResponseDTO toResponseDTO(Cart cart) {

        List<CartItemResponseDTO> items =
                cart.getItems()
                        .stream()
                        .map(item -> new CartItemResponseDTO(
                                item.getProduct().getId(),
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getProduct().getPrice(),
                                item.getSubtotal()
                        ))
                        .toList();

        return new CartResponseDTO(
                cart.getId(),
                cart.getTotalPrice(),
                items
        );
    }

    private Cart getOrCreateCart(User user) {

        return cartRepository.findByUser(user)
                .orElseGet(() -> {

                    Cart cart = new Cart();

                    cart.setUser(user);

                    return cartRepository.save(cart);
                });
    }

    public CartResponseDTO addProduct(
            Long productId,
            Integer quantity
    ) {

        User user = getAuthenticatedUser();

        Cart cart = getOrCreateCart(user);

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Insufficient stock");
        }

        CartItem existingItem = cart.getItems()
                .stream()
                .filter(item ->
                        item.getProduct()
                                .getId()
                                .equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {

            existingItem.setQuantity(
                    existingItem.getQuantity() + quantity
            );

            existingItem.setSubtotal(
                    product.getPrice().multiply(
                            BigDecimal.valueOf(
                                    existingItem.getQuantity()
                            )
                    )
            );

        } else {

            CartItem item = new CartItem();

            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);

            item.setSubtotal(
                    product.getPrice().multiply(
                            BigDecimal.valueOf(quantity)
                    )
            );

            cart.getItems().add(item);
        }

        updateCartTotal(cart);

        Cart savedCart = cartRepository.save(cart);

        return toResponseDTO(savedCart);
    }

    public CartResponseDTO getCart() {

        User user = getAuthenticatedUser();

        Cart cart = getOrCreateCart(user);

        return toResponseDTO(cart);
    }

    public void removeProduct(Long productId) {

        User user = getAuthenticatedUser();

        Cart cart = getOrCreateCart(user);

        cart.getItems().removeIf(item ->
                item.getProduct()
                        .getId()
                        .equals(productId)
        );

        updateCartTotal(cart);

        cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {

        BigDecimal total = cart.getItems()
                .stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(total);
    }
}