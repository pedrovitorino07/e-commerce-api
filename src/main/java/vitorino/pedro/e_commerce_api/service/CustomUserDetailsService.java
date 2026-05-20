package vitorino.pedro.e_commerce_api.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.entity.User;
import vitorino.pedro.e_commerce_api.repository.UserRepository;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole()
                        )
                )
        );
    }
}