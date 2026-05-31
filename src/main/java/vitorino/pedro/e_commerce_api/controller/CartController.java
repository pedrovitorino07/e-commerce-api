package vitorino.pedro.e_commerce_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vitorino.pedro.e_commerce_api.dto.AddToCartDTO;
import vitorino.pedro.e_commerce_api.dto.CartResponseDTO;
import vitorino.pedro.e_commerce_api.service.CartService;

@Tag(name = "Cart",
        description = "Carrinho de compras")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public CartResponseDTO getCart() {
        return cartService.getCart();
    }

    @PostMapping("/add/{productId}")
    public CartResponseDTO addProduct(
            @PathVariable Long productId,
            @Valid @RequestBody AddToCartDTO dto
    ) {
        return cartService.addProduct(
                productId,
                dto.quantity()
        );
    }

    @DeleteMapping("/remove/{productId}")
    public void removeProduct(
            @PathVariable Long productId
    ) {

        cartService.removeProduct(productId);
    }
}