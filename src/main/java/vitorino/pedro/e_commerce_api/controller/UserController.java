package vitorino.pedro.e_commerce_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.UserRequestDTO;
import vitorino.pedro.e_commerce_api.dto.UserResponseDTO;
import vitorino.pedro.e_commerce_api.service.UserService;

@Tag(name = "Users", description = "Perfil do usuário autenticado")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserResponseDTO getProfile() {

        return userService.getProfile();
    }

    @PutMapping("/me")
    public UserResponseDTO updateProfile(@Valid @RequestBody UserRequestDTO dto) {

        return userService.updateProfile(dto);
    }

    @DeleteMapping("/me")
    public void deleteProfile() {

        userService.deleteProfile();
    }
}
