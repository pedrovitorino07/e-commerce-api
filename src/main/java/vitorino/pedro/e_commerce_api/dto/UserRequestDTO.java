package vitorino.pedro.e_commerce_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Email
        String email,

        @Size(min = 6)
        String password

) {
}