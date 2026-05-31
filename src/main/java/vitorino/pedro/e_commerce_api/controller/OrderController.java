package vitorino.pedro.e_commerce_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.OrderResponseDTO;
import vitorino.pedro.e_commerce_api.dto.ProductResponseDTO;
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
    public Page<OrderResponseDTO> getMyOrders(
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt")
            Pageable pageable
    ) {
        return orderService.getMyOrders(pageable);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(
            @PathVariable Long id
    ) {
        return orderService.getOrderById(id);
    }
}