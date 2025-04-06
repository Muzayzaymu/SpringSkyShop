package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixCostProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StorageService {

    private final Map<UUID, Product> productStorage = new HashMap<>();
    private final Map<UUID, Article> articleStorage = new HashMap<>();

    public StorageService() {
        initializeData();
    }

    private void initializeData() {
        UUID appleId = UUID.randomUUID();
        UUID bananaId = UUID.randomUUID();
        UUID gumId = UUID.randomUUID();
        UUID orangeId = UUID.randomUUID();
        UUID chocolateId = UUID.randomUUID();

        productStorage.put(appleId, new SimpleProduct(appleId, "Яблоко", 100.0));
        productStorage.put(bananaId, new DiscountedProduct(bananaId, "Банан со скидкой", 150.0, 10));
        productStorage.put(gumId, new FixCostProduct(gumId, "Жвачка"));
        productStorage.put(orangeId, new SimpleProduct(orangeId, "Апельсин", 120.0));
        productStorage.put(chocolateId, new DiscountedProduct(chocolateId, "Шоколад со скидкой", 200.0, 20));


        UUID article1Id = UUID.randomUUID();
        UUID article2Id = UUID.randomUUID();

        articleStorage.put(article1Id, new Article(article1Id, "Как выбрать яблоки", "В этой статье мы расскажем о том, как правильно выбирать яблоки."));
        articleStorage.put(article2Id, new Article(article2Id, "Польза бананов", "Бананы - отличный источник калия и энергии."));
    }

    public Collection<Product> getAllProducts() {
        return productStorage.values();
    }

    public Collection<Article> getAllArticles() {
        return articleStorage.values();
    }

    public Collection<Searchable> getAllSearchableItems() {
        List<Searchable> allItems = new ArrayList<>();
        allItems.addAll(productStorage.values());
        allItems.addAll(articleStorage.values());
        return allItems;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productStorage.get(id));
    }
}