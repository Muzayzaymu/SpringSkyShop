package org.skypro.skyshop.model;

import org.skypro.skyshop.model.basket.BasketItem;

import java.util.List;

public final class UserBasket {
    private final List<BasketItem> items;
    private final double total;

    public UserBasket(List<BasketItem> items, double total) {
        this.items = items;
        this.total = total;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}