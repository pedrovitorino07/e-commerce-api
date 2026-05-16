package vitorino.pedro.e_commerce_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vitorino.pedro.e_commerce_api.dto.ProductRequestDTO;
import vitorino.pedro.e_commerce_api.dto.ProductResponseDTO;
import vitorino.pedro.e_commerce_api.entity.Product;
import vitorino.pedro.e_commerce_api.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private ProductResponseDTO toResponseDTO(Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getBrand(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getImageUrl(),
                product.getActive()
        );
    }

    private Product toEntity(ProductRequestDTO dto) {

        Product product = new Product();

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setBrand(dto.brand());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setCategory(dto.category());
        product.setImageUrl(dto.imageUrl());
        product.setActive(true);

        return product;
    }

    public List<ProductResponseDTO> findAll() {

        return productRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO findById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return toResponseDTO(product);
    }

    public List<ProductResponseDTO> findByName(String name) {

        List<Product> products =
                productRepository.findByNameContainingIgnoreCase(name);

        return products.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO save(ProductRequestDTO dto) {

        Product product = toEntity(dto);

        Product savedProduct = productRepository.save(product);

        return toResponseDTO(savedProduct);
    }

    public List<ProductResponseDTO> saveAll(List<ProductRequestDTO> dtos) {

        List<Product> products = dtos.stream()
                .map(this::toEntity)
                .toList();

        List<Product> savedProducts =
                productRepository.saveAll(products);

        return savedProducts.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
