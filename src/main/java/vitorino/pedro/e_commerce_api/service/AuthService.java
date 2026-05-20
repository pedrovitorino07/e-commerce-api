package vitorino.pedro.e_commerce_api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.dto.LoginRequestDTO;
import vitorino.pedro.e_commerce_api.dto.LoginResponseDTO;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.repository.UserRepository;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final JwtService jwtService;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository repository,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        User user = repository.findByEmail(dto.email())
                .orElseThrow();

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(token);
    }
}