package vitorino.pedro.e_commerce_api.dto;

import vitorino.pedro.e_commerce_api.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(

        Long id,
        BigDecimal totalPrice,
        OrderStatus status,
        LocalDateTime createdAt,
        List<OrderItemResponseDTO> items
) {
}
