package vitorino.pedro.e_commerce_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void prepareUser(User user) {

        boolean exists = userRepository.existsByEmail(user.getEmail());

        if (exists) {
            throw new RuntimeException(
                    "User with email " + user.getEmail() + " already exists"
            );
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {

        prepareUser(user);

        return userRepository.save(user);
    }

    public List<User> saveAll(List<User> users) {

        for (User user : users) {
            prepareUser(user);
        }

        return userRepository.saveAll(users);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
