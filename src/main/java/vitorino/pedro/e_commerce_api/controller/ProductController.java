package vitorino.pedro.e_commerce_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.ProductRequestDTO;
import vitorino.pedro.e_commerce_api.dto.ProductResponseDTO;
import vitorino.pedro.e_commerce_api.enums.Category;
import vitorino.pedro.e_commerce_api.service.ProductService;

import java.util.List;

@Tag(name = "Products",
        description = "Gerenciamento de produtos")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<ProductResponseDTO> getProducts(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name")
            Pageable pageable
    ) {
        return productService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/search")
    public List<ProductResponseDTO> search(@RequestParam String name) {
        return productService.findByName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        return productService.update(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponseDTO saveProduct(@Valid @RequestBody ProductRequestDTO dto) {
        return productService.save(dto);
    }

    @GetMapping("/category/{category}")
    public List<ProductResponseDTO> findByCategory(@PathVariable Category category) {

        return productService.findByCategory(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }
}