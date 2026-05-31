package vitorino.pedro.e_commerce_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.dto.UserRequestDTO;
import vitorino.pedro.e_commerce_api.dto.UserResponseDTO;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.exception.EmailAlreadyExistsException;
import vitorino.pedro.e_commerce_api.exception.UserNotFoundException;
import vitorino.pedro.e_commerce_api.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void validateEmail(String email) {

        boolean exists = userRepository.existsByEmail(email);

        if (exists) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }
    }

    private UserResponseDTO toResponseDTO(User user) {

        return new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }

    public UserResponseDTO getProfile() {

        User user = getAuthenticatedUser();

        return toResponseDTO(user);
    }

    public UserResponseDTO updateProfile(UserRequestDTO dto) {

        User user = getAuthenticatedUser();

        if (!user.getEmail().equals(dto.email())) {
            validateEmail(dto.email());
        }

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());

        user.setPassword(passwordEncoder.encode(dto.password()));

        User updatedUser = userRepository.save(user);

        return toResponseDTO(updatedUser);
    }

    public void deleteProfile() {

        User user = getAuthenticatedUser();

        userRepository.delete(user);

    }
}
