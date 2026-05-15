package vitorino.pedro.e_commerce_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
