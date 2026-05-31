package vitorino.pedro.e_commerce_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @Schema(
                description = "Primeiro nome do usuário",
                example = "João"
        )
        @NotBlank
        String firstName,

        @Schema(
                description = "Último nome do usuário",
                example = "Silva"
        )
        @NotBlank
        String lastName,

        @Schema(
                description = "Email do usuário",
                example = "joao@email.com"
        )
        @Email
        String email,

        @Schema(
                description = "Senha do usuário",
                example = "joao123"
        )
        @Size(min = 6)
        String password

) {
}