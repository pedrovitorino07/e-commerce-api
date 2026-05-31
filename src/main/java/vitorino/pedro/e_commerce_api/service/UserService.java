package vitorino.pedro.e_commerce_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.dto.LoginRequestDTO;
import vitorino.pedro.e_commerce_api.dto.UserRequestDTO;
import vitorino.pedro.e_commerce_api.dto.UserResponseDTO;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.enums.Role;
import vitorino.pedro.e_commerce_api.exception.EmailAlreadyExistsException;
import vitorino.pedro.e_commerce_api.exception.UserNotFoundException;
import vitorino.pedro.e_commerce_api.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User prepareUser(UserRequestDTO dto) {

        User user = new User();

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setRole(Role.USER);

        String encodedPassword = passwordEncoder.encode(dto.password());

        user.setPassword(encodedPassword);

        return user;
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

    public List<UserResponseDTO> findAll() {

        return userRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public UserResponseDTO findById(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));

        return toResponseDTO(user);
    }

    public UserResponseDTO update(Long id, UserRequestDTO dto) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));

        if (!user.getEmail().equals(dto.email())) {

            validateEmail(dto.email());
        }

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());

        String encodedPassword = passwordEncoder.encode(dto.password());

        user.setPassword(encodedPassword);

        User updatedUser = userRepository.save(user);

        return toResponseDTO(updatedUser);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
