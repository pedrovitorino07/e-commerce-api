package vitorino.pedro.e_commerce_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitorino.pedro.e_commerce_api.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
