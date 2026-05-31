package vitorino.pedro.e_commerce_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.UserRequestDTO;
import vitorino.pedro.e_commerce_api.dto.UserResponseDTO;
import vitorino.pedro.e_commerce_api.repository.UserRepository;
import vitorino.pedro.e_commerce_api.service.UserService;

import java.util.List;

@Tag(name = "Users",
        description = "Gerenciamento de usuários")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
        return userService.update(id, dto);
    }

    @PostMapping("/save")
    public UserResponseDTO save(
            @Valid @RequestBody UserRequestDTO dto
    ) {
        return userService.save(dto);
    }

    @PostMapping("/save-all")
    public List<UserResponseDTO> saveAll(
            @Valid @RequestBody List<UserRequestDTO> dtos
    ) {
        return userService.saveAll(dtos);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
