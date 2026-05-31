package vitorino.pedro.e_commerce_api.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.dto.OrderItemResponseDTO;
import vitorino.pedro.e_commerce_api.dto.OrderResponseDTO;
import vitorino.pedro.e_commerce_api.entity.*;
import vitorino.pedro.e_commerce_api.enums.OrderStatus;
import vitorino.pedro.e_commerce_api.repository.CartRepository;
import vitorino.pedro.e_commerce_api.repository.OrderRepository;
import vitorino.pedro.e_commerce_api.repository.ProductRepository;
import vitorino.pedro.e_commerce_api.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }

    private OrderResponseDTO toResponseDTO(Order order) {

        List<OrderItemResponseDTO> items =
                order.getItems()
                        .stream()
                        .map(item -> new OrderItemResponseDTO(
                                item.getProductId(),
                                item.getProductName(),
                                item.getPrice(),
                                item.getQuantity(),
                                item.getSubtotal()
                        ))
                        .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt(),
                items
        );
    }

    @Transactional
    public OrderResponseDTO checkout() {

        User user = getAuthenticatedUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found")
                );

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }

            product.setStock(
                    product.getStock() - cartItem.getQuantity()
            );

            productRepository.save(product);

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSubtotal(cartItem.getSubtotal());

            order.getItems().add(orderItem);
        }

        BigDecimal total = order.getItems()
                .stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(total);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);

        cartRepository.save(cart);

        return toResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> getMyOrders() {

        User user = getAuthenticatedUser();

        return orderRepository.findByUser(user)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public OrderResponseDTO getOrderById(Long id) {

        User user = getAuthenticatedUser();

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found")
                );

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return toResponseDTO(order);
    }
}