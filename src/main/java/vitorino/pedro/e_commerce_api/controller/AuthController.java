package vitorino.pedro.e_commerce_api.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.LoginRequestDTO;
import vitorino.pedro.e_commerce_api.dto.LoginResponseDTO;
import vitorino.pedro.e_commerce_api.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

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
}