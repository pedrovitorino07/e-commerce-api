package vitorino.pedro.e_commerce_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitorino.pedro.e_commerce_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
