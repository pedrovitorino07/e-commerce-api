package vitorino.pedro.e_commerce_api.dto;


import java.time.LocalDateTime;

public record ErrorResponseDTO(

        int status,

        String errorMessage,

        String message,

        LocalDateTime timestamp
) {

    public ErrorResponseDTO(
            int status,
            String errorMessage,
            String message
    ) {
        this(
                status,
                errorMessage,
                message,
                LocalDateTime.now());
    }
}
