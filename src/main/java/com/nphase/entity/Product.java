package com.nphase.entity;

import com.nphase.common.Category;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final String name;
    private final BigDecimal pricePerUnit;
    private int quantity;

    private BigDecimal totalPrice;
    private final Category category;

    public Product(String name, BigDecimal pricePerUnit, int quantity, Category category) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.totalPrice = pricePerUnit.multiply(BigDecimal.valueOf(quantity));
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        actualizeTotalPrice();
        return this;
    }

    private void actualizeTotalPrice() {
        this.totalPrice = pricePerUnit.multiply(BigDecimal.valueOf(quantity));
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity && Objects.equals(name, product.name) && Objects.equals(pricePerUnit, product.pricePerUnit) && Objects.equals(totalPrice, product.totalPrice) && category == product.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pricePerUnit, quantity, totalPrice, category);
    }
}
