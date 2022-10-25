package com.nphase.service;


import com.nphase.common.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.repositories.InMemoryProductsCartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public class InMemoryProductsCartRepositoryTest {
    private InMemoryProductsCartRepository repository;

    @BeforeEach
    public void initialize() {
        repository = new InMemoryProductsCartRepository();
    }

    @Test
    public void addOneProduct()  {
        final String Coffee = "Coffee";

        repository.add(new Product(Coffee, BigDecimal.valueOf(6.5), 1, Category.DRINKS));

        Map<String, Product> products = repository.getAllMap();

        Assertions.assertEquals(products.size(), 1);
        Product product = products.get(Coffee);
        Assertions.assertEquals(product.getQuantity(), 1);
        Assertions.assertEquals(product.getTotalPrice(), BigDecimal.valueOf(6.5));
    }

    @Test
    public void addTwoSameProducts()  {
        final String Coffee = "Coffee";

        repository.add(new Product(Coffee, BigDecimal.valueOf(6.5), 1, Category.DRINKS));
        repository.add(new Product(Coffee, BigDecimal.valueOf(6.5), 1, Category.DRINKS));

        Map<String, Product> products = repository.getAllMap();

        Assertions.assertEquals(products.size(), 1);
        Product product = products.get(Coffee);
        Assertions.assertEquals(product.getQuantity(), 2);
        Assertions.assertEquals(product.getTotalPrice(), BigDecimal.valueOf(13.00));
    }

    @Test
    public void addTwoKindOfTwoDifferentProducts()  {
        final String Coffee = "Coffee";
        final String Cheese = "Cheese";

        repository.add(new Product(Coffee, BigDecimal.valueOf(6.5), 1, Category.DRINKS));
        repository.add(new Product(Coffee, BigDecimal.valueOf(6.5), 1, Category.DRINKS));

        repository.add(new Product(Cheese, BigDecimal.valueOf(8.0), 1, Category.FOODS));
        repository.add(new Product(Cheese, BigDecimal.valueOf(8.0), 1, Category.FOODS));

        Map<String, Product> products = repository.getAllMap();

        Assertions.assertEquals(products.size(), 2);
        Product coffeeProduct = products.get(Coffee);
        Assertions.assertEquals(coffeeProduct.getQuantity(), 2);
        Assertions.assertEquals(coffeeProduct.getTotalPrice(), BigDecimal.valueOf(13.00));

        Product cheeseProduct = products.get(Cheese);
        Assertions.assertEquals(cheeseProduct.getQuantity(), 2);
        Assertions.assertEquals(cheeseProduct.getTotalPrice(), BigDecimal.valueOf(16.00));
    }

    @Test
    public void addTwoSameProductsThenRemoveOne()  {
        final String Coffee = "Coffee";

        repository.add(new Product(Coffee, BigDecimal.valueOf(6.5), 1, Category.DRINKS));
        repository.add(new Product(Coffee, BigDecimal.valueOf(6.5), 1, Category.DRINKS));

        Map<String, Product> products = repository.getAllMap();

        Assertions.assertEquals(products.size(), 1);
        Product product = products.get(Coffee);
        Assertions.assertEquals(product.getQuantity(), 2);
        Assertions.assertEquals(product.getTotalPrice(), BigDecimal.valueOf(13.00));

        repository.delete(new Product(Coffee, BigDecimal.valueOf(6.5), 1, Category.DRINKS));
        Map<String, Product> productsAfterRemove = repository.getAllMap();

        Assertions.assertEquals(products.size(), 1);
        Product product2 = productsAfterRemove.get(Coffee);
        Assertions.assertEquals(product2.getQuantity(), 1);
        Assertions.assertEquals(product2.getTotalPrice(), BigDecimal.valueOf(6.5));
    }


}