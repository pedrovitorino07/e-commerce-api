package vitorino.pedro.e_commerce_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> saveAll(List<User> users) {
        return userRepository.saveAll(users);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
