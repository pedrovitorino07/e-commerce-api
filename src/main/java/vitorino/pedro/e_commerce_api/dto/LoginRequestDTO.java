package vitorino.pedro.e_commerce_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record LoginRequestDTO(

        @Schema(
                description = "Email do usuário",
                example = "pedro@email.com"
        )
        String email,

        @Schema(
                description = "Senha do usuário",
                example = "123456"
        )
        String password

) {
}
