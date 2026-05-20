package vitorino.pedro.e_commerce_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import vitorino.pedro.e_commerce_api.enums.Category;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @NotBlank(message = "Brand is required")
        String brand,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock cannot be negative")
        Integer stock,

        @NotNull(message = "Category is required")
        Category category,

        @NotBlank(message = "Image URL is required")
        String imageUrl
) {
}
