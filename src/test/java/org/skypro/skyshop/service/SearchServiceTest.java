package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.SearchResult;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.Searchable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    private SearchService searchService;
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = Mockito.mock(StorageService.class);
        searchService = new SearchService(storageService);
    }

    @Test
    void search_noItemsInStorage() {
        Mockito.when(storageService.getAllSearchableItems()).thenReturn(Collections.emptyList());

        List<SearchResult> results = (List<SearchResult>) searchService.search("test");

        assertTrue(results.isEmpty());
    }

    @Test
    void search_itemsExistButNoMatch() {
        List<Searchable> items = new ArrayList<>();
        items.add(new Article(UUID.randomUUID(), "Яблоки", "Текст про яблоки"));
        items.add(new Product(UUID.randomUUID(), "Бананы") {
            @Override
            public double getCost() {
                return 0;
            }
        });

        Mockito.when(storageService.getAllSearchableItems()).thenReturn(items);

        List<SearchResult> results = (List<SearchResult>) searchService.search("test");

        assertTrue(results.isEmpty());
    }

    @Test
    void search_matchingItemExists() {
        Article article = new Article(UUID.randomUUID(), "Test Article", "This is a test article.");
        List<Searchable> items = Collections.singletonList(article);

        Mockito.when(storageService.getAllSearchableItems()).thenReturn(items);

        List<SearchResult> results = (List<SearchResult>) searchService.search("test");

        assertEquals(1, results.size());
        SearchResult result = results.getFirst();
        assertEquals(article.getId().toString(), result.getId());
        assertEquals("Test Article", result.getName());
        assertEquals("ARTICLE", result.getContentType());
    }

    @Test
    void search_caseInsensitive() {
        Article article = new Article(UUID.randomUUID(), "Test Article", "This is a test article.");
        List<Searchable> items = Collections.singletonList(article);

        Mockito.when(storageService.getAllSearchableItems()).thenReturn(items);

        List<SearchResult> results = (List<SearchResult>) searchService.search("TEST");

        assertEquals(1, results.size());
        SearchResult result = results.getFirst();
        assertEquals(article.getId().toString(), result.getId());
    }
}