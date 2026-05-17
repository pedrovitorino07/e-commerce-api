package vitorino.pedro.e_commerce_api.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(

        int status,
        String message,
        LocalDateTime timestamp
) {
}
