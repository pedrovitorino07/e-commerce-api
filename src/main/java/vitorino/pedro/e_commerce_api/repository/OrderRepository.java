package vitorino.pedro.e_commerce_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vitorino.pedro.e_commerce_api.entity.Order;
import vitorino.pedro.e_commerce_api.entity.User;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    Page<Order> findByUser(User user, Pageable pageable);
}