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

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.USER);

        String encodedPassword =
                passwordEncoder.encode(dto.getPassword());

        user.setPassword(encodedPassword);

        return user;
    }

    private void validateEmail(String email) {

        boolean exists = userRepository.existsByEmail(email);

        if (exists) {
            throw new EmailAlreadyExistsException(
                    "Email already exists!"
            );
        }
    }

    private UserResponseDTO toResponseDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserResponseDTO save(UserRequestDTO dto) {

        validateEmail(dto.getEmail());

        User user = prepareUser(dto);

        User savedUser = userRepository.save(user);

        return toResponseDTO(savedUser);
    }

    public List<UserResponseDTO> saveAll(List<UserRequestDTO> dtos) {

        List<User> users = new ArrayList<>();

        for (UserRequestDTO dto : dtos) {

            validateEmail(dto.getEmail());

            users.add(prepareUser(dto));
        }

        List<User> savedUsers = userRepository.saveAll(users);

        return savedUsers
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
