package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.UserBasket;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProductToBasket(UUID id) {
        Product product = storageService.getProductById(id)
                .orElseThrow(() -> new NoSuchProductException("Product with id " + id + " not found"));
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        List<BasketItem> basketItems = productBasket.getProducts().entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(productId).orElseThrow(() -> new NoSuchProductException("Product not found: " + productId));
                    return new BasketItem(product, quantity);
                })
                .collect(Collectors.toList());

        double total = basketItems.stream()
                .mapToDouble(item -> item.getProduct().getCost() * item.getQuantity())
                .sum();

        return new UserBasket(basketItems, total);
    }
}