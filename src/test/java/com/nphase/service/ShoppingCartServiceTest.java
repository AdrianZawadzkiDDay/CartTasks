package com.nphase.service;


import com.nphase.common.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.repositories.InMemoryProductsCartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private ShoppingCartService service;
    private InMemoryProductsCartRepository repository;

    @BeforeEach
    public void initialize() {
        repository = new InMemoryProductsCartRepository();
        service = new ShoppingCartService(repository);
    }

    @Test
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, Category.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1, Category.DRINKS)
        ));
        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

    @Test
    public void task1calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 1, Category.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, Category.DRINKS)
        ));
        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(12.0));
    }

    @Test
    public void task2CalculatesPriceWithDiscount()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, Category.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, Category.DRINKS)
        ));
        BigDecimal result = service.calculateTotalPriceWithDiscount(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(33.00).setScale(2));
    }

    @Test
    public void task3CalculatesPriceWithDiscountCategory()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, Category.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, Category.DRINKS),
                new Product("Cheese", BigDecimal.valueOf(8), 2, Category.FOODS)
        ));
        BigDecimal result = service.calculateTotalPriceWithCategoryDiscount(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(31.84).setScale(2));
    }

}