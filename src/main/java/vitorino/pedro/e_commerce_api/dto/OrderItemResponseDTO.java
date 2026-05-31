package vitorino.pedro.e_commerce_api.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(

        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity,
        BigDecimal subtotal
) {
}
