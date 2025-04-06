package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private double baseCost;
    private int discountPercentage;

    public DiscountedProduct(UUID id, String name, double baseCost, int discountPercentage) {
        super(id, name);

        if (baseCost <= 0) {
            throw new IllegalArgumentException("Base cost must be greater than 0.");
        }

        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100.");
        }

        this.baseCost = baseCost;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getCost() {
        return baseCost * (1 - (double) discountPercentage / 100);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": " + getCost() + " (" + discountPercentage + "%)";
    }
}