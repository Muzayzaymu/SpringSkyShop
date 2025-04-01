package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixCostProduct extends Product {
    private static final double FIXED_PRICE = 50.0;

    public FixCostProduct(UUID id, String name) {
        super(id, name);
    }

    @Override
    public double getCost() {
        return FIXED_PRICE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": Фиксированная цена " + FIXED_PRICE;
    }
}