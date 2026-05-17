package vitorino.pedro.e_commerce_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.ProductRequestDTO;
import vitorino.pedro.e_commerce_api.dto.ProductResponseDTO;
import vitorino.pedro.e_commerce_api.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<ProductResponseDTO> getProducts(
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
    public List<ProductResponseDTO> search(
            @RequestParam String name
    ) {
        return productService.findByName(name);
    }

    @PostMapping("/save")
    public ProductResponseDTO saveProduct(
            @Valid @RequestBody ProductRequestDTO dto
    ) {
        return productService.save(dto);
    }

    @PostMapping("/save-all")
    public List<ProductResponseDTO> saveAll(
            @Valid @RequestBody List<ProductRequestDTO> dtos
    ) {
        return productService.saveAll(dtos);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }
}