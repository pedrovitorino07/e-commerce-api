package vitorino.pedro.e_commerce_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vitorino.pedro.e_commerce_api.entity.Product;
import vitorino.pedro.e_commerce_api.enums.Category;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByActiveTrue(Pageable pageable);

    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(
            String name
    );

    List<Product> findByCategoryAndActiveTrue(
            Category category
    );
}
