package vitorino.pedro.e_commerce_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitorino.pedro.e_commerce_api.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
