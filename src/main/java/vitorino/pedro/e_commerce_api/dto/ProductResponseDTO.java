package vitorino.pedro.e_commerce_api.dto;

import vitorino.pedro.e_commerce_api.enums.Category;

import java.math.BigDecimal;

public record ProductResponseDTO(

        Long id,
        String name,
        String description,
        String brand,
        BigDecimal price,
        Integer stock,
        Category category,
        String imageUrl,
        Boolean active
) {
}
