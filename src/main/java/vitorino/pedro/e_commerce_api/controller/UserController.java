package vitorino.pedro.e_commerce_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.LoginRequestDTO;
import vitorino.pedro.e_commerce_api.dto.UserRequestDTO;
import vitorino.pedro.e_commerce_api.dto.UserResponseDTO;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.service.AuthService;
import vitorino.pedro.e_commerce_api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
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
