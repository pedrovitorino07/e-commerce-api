package vitorino.pedro.e_commerce_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.OrderResponseDTO;
import vitorino.pedro.e_commerce_api.entity.Order;
import vitorino.pedro.e_commerce_api.service.OrderService;

import java.util.List;

@Tag(name = "Orders",
        description = "Pedidos dos usuários")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public OrderResponseDTO checkout() {
        return orderService.checkout();
    }

    @GetMapping("/my-orders")
    public List<OrderResponseDTO> getMyOrders() {
        return orderService.getMyOrders();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(
            @PathVariable Long id
    ) {
        return orderService.getOrderById(id);
    }
}