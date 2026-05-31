package vitorino.pedro.e_commerce_api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.dto.LoginRequestDTO;
import vitorino.pedro.e_commerce_api.dto.LoginResponseDTO;
import vitorino.pedro.e_commerce_api.dto.UserRequestDTO;
import vitorino.pedro.e_commerce_api.dto.UserResponseDTO;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.enums.Role;
import vitorino.pedro.e_commerce_api.exception.EmailAlreadyExistsException;
import vitorino.pedro.e_commerce_api.repository.UserRepository;
import vitorino.pedro.e_commerce_api.service.JwtService;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository repository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(UserRequestDTO dto) {

        if (repository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        User user = new User();

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setRole(Role.USER);

        String encodedPassword =
                passwordEncoder.encode(dto.password());

        user.setPassword(encodedPassword);

        User savedUser = repository.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
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