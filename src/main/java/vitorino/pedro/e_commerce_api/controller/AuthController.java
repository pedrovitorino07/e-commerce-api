package vitorino.pedro.e_commerce_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.LoginRequestDTO;
import vitorino.pedro.e_commerce_api.dto.LoginResponseDTO;
import vitorino.pedro.e_commerce_api.dto.UserRequestDTO;
import vitorino.pedro.e_commerce_api.dto.UserResponseDTO;
import vitorino.pedro.e_commerce_api.service.AuthService;

@Tag(name = "Authentication",
        description = "Autenticação e geração de JWT")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO dto
    ) {

        return authService.login(dto);
    }

    @PostMapping("/register")
    public UserResponseDTO register(
            @Valid @RequestBody UserRequestDTO dto
    ) {
        return authService.register(dto);
    }
}