package vitorino.pedro.e_commerce_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vitorino.pedro.e_commerce_api.enums.Role;

public record UserResponseDTO (
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role
) {

}