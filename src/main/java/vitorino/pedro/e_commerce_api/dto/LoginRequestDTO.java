package vitorino.pedro.e_commerce_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record LoginRequestDTO(

        String email,
        String password

) {
}
