package vitorino.pedro.e_commerce_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.entity.Product;
import vitorino.pedro.e_commerce_api.repository.ProductRepository;
import vitorino.pedro.e_commerce_api.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("/save")
    public Product saveProduct(@Valid @RequestBody Product product) {
        return productService.save(product);
    }

    @PostMapping("/save-all")
    public List<Product> saveAllProducts(@Valid @RequestBody List<Product> products) {
        return productService.saveAll(products);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
