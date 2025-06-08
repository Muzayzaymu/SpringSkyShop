package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.UserBasket;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    private BasketService basketService;
    private ProductBasket productBasket;
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        productBasket = Mockito.mock(ProductBasket.class);
        storageService = Mockito.mock(StorageService.class);
        basketService = new BasketService(productBasket, storageService);
    }

    @Test
    void addProductToBasket_nonExistingProduct_throwsException() {
        UUID nonExistingId = UUID.randomUUID();
        Mockito.when(storageService.getProductById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.addProductToBasket(nonExistingId));
    }

    @Test
    void addProductToBasket_existingProduct_callsAddProductOnProductBasket() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product") {
            @Override
            public double getCost() {
                return 100.0;
            }
        };

        Mockito.when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        basketService.addProductToBasket(productId);

        verify(productBasket, times(1)).addProduct(productId);
    }

    @Test
    void getUserBasket_emptyProductBasket_returnsEmptyBasket() {
        Mockito.when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        UserBasket basket = basketService.getUserBasket();

        assertTrue(basket.getItems().isEmpty());
        assertEquals(0.0, basket.getTotal());
    }

    @Test
    void getUserBasket_productsInProductBasket_returnsBasketWithItems() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product") {
            @Override
            public double getCost() {
                return 100.0;
            }
        };

        Mockito.when(productBasket.getProducts()).thenReturn(Collections.singletonMap(productId, 2));
        Mockito.when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        UserBasket basket = basketService.getUserBasket();

        assertEquals(1, basket.getItems().size());
        assertEquals(200.0, basket.getTotal());
    }

    @Test
    void getUserBasket_productNotFoundInStorage_throwsException() {
        UUID productId = UUID.randomUUID();
        Mockito.when(productBasket.getProducts()).thenReturn(Collections.singletonMap(productId, 1));
        Mockito.when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.getUserBasket());
    }
}