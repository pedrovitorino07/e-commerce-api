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
import vitorino.pedro.e_commerce_api.exception.*;
import vitorino.pedro.e_commerce_api.repository.CartRepository;
import vitorino.pedro.e_commerce_api.repository.OrderRepository;
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
    private UserRepository userRepository;

    private User getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private OrderResponseDTO toResponseDTO(Order order) {

        List<OrderItemResponseDTO> items = order.getItems().stream().map(item -> new OrderItemResponseDTO(item.getProductId(), item.getProductName(), item.getPrice(), item.getQuantity(), item.getSubtotal())).toList();

        return new OrderResponseDTO(order.getId(), order.getTotalPrice(), order.getStatus(), order.getCreatedAt(), items);
    }

    private Cart getOrCreateCart(User user) {

        return cartRepository.findByUser(user).orElseGet(() -> {

            Cart cart = new Cart();

            cart.setUser(user);

            return cartRepository.save(cart);
        });
    }

    private OrderItem createOrderItem(Order order, CartItem cartItem) {

        Product product = cartItem.getProduct();

        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setPrice(product.getPrice());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setSubtotal(cartItem.getSubtotal());

        return orderItem;
    }


    @Transactional
    public OrderResponseDTO checkout() {

        User user = getAuthenticatedUser();

        Cart cart = getOrCreateCart(user);

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Não é possível finalizar uma compra com o carrinho vazio");
        }

        Order order = new Order();

        order.setUser(user);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - cartItem.getQuantity());

            order.getItems().add(createOrderItem(order, cartItem));

            total = total.add(cartItem.getSubtotal());
        }

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

        return orderRepository.findByUser(user).stream().map(this::toResponseDTO).toList();
    }

    public OrderResponseDTO getOrderById(Long id) {

        User user = getAuthenticatedUser();

        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Você não tem permissão para acessar este pedido");
        }

        return toResponseDTO(order);
    }
}