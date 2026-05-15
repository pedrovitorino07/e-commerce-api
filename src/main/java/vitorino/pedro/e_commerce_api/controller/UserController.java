package vitorino.pedro.e_commerce_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.entity.User;
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
    public List<User> create(@RequestBody List<User> users) {
        return userService.saveAll(users);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
