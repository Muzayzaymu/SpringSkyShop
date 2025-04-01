package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private final double cost;

    public SimpleProduct(UUID id, String name, double cost) {
        super(id, name);

        if (cost <= 0) {
            throw new IllegalArgumentException("Cost must be greater than 0.");
        }

        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }
}