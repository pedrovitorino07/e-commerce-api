package vitorino.pedro.e_commerce_api.dto;

import jakarta.validation.constraints.Positive;

public record AddToCartDTO(

        @Positive
        Integer quantity
) {
}
