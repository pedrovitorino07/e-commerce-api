package vitorino.pedro.e_commerce_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ErrorResponseDTO(

        int status,

        String message,

        LocalDateTime timestamp
) {
}
