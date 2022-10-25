package com.nphase.service;


import com.nphase.common.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.repositories.InMemoryProductsCartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ShoppingCartServiceConfigurableTest {
    private ShoppingCartService service;
    private InMemoryProductsCartRepository repository;

    @BeforeEach
    public void initialize() {
        repository = new InMemoryProductsCartRepository();
        service = new ShoppingCartService(repository);
    }

    @Test
    public void calculatesPriceOfTwoProducts()  {
        service.addProduct(new Product("Tea", BigDecimal.valueOf(5.0), 1, Category.DRINKS));
        service.addProduct(new Product("Tea", BigDecimal.valueOf(5.0), 1, Category.DRINKS));
        service.addProduct(new Product("Coffee", BigDecimal.valueOf(6.5), 1, Category.DRINKS));

        List<Product> productList = service.getProductsCartRepository().getAll();
        ShoppingCart shoppingCart = new ShoppingCart(productList);

        BigDecimal result =
                service.calculateTotalPriceWithCategoryDiscountConfigurableDiscount(shoppingCart, 0.9, 0.9);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

    @Test
    public void configurableCalculateTask2()  {
        // add 5 teas
        service.addProduct( new Product("Tea", BigDecimal.valueOf(5.0), 1, Category.DRINKS));
        service.addProduct( new Product("Tea", BigDecimal.valueOf(5.0), 1, Category.DRINKS));
        service.addProduct( new Product("Tea", BigDecimal.valueOf(5.0), 1, Category.DRINKS));
        service.addProduct( new Product("Tea", BigDecimal.valueOf(5.0), 1, Category.DRINKS));
        service.addProduct( new Product("Tea", BigDecimal.valueOf(5.0), 1, Category.DRINKS));
        // add 3 coffees
        service.addProduct(new Product("Coffee", BigDecimal.valueOf(3.5), 1, Category.DRINKS));
        service.addProduct(new Product("Coffee", BigDecimal.valueOf(3.5), 1, Category.DRINKS));
        service.addProduct(new Product("Coffee", BigDecimal.valueOf(3.5), 1, Category.DRINKS));

        List<Product> productList = service.getProductsCartRepository().getAll();
        ShoppingCart shoppingCart = new ShoppingCart(productList);

        BigDecimal result =
                service.calculateTotalPriceWithCategoryDiscountConfigurableDiscount(shoppingCart, 0.9, 1);

        Assertions.assertEquals(result, BigDecimal.valueOf(33.00).setScale(2));
    }

    @Test
    public void task3CalculatesPriceWithDiscountCategory()  {
        service.addProduct(new Product("Tea", BigDecimal.valueOf(5.3), 1, Category.DRINKS));
        service.addProduct(new Product("Tea", BigDecimal.valueOf(5.3), 1, Category.DRINKS));

        service.addProduct(new Product("Coffee", BigDecimal.valueOf(3.5), 1, Category.DRINKS));
        service.addProduct(new Product("Coffee", BigDecimal.valueOf(3.5), 1, Category.DRINKS));

        service.addProduct(new Product("Cheese", BigDecimal.valueOf(8), 1, Category.FOODS));
        service.addProduct(new Product("Cheese", BigDecimal.valueOf(8), 1, Category.FOODS));


        List<Product> productList = service.getProductsCartRepository().getAll();
        ShoppingCart shoppingCart = new ShoppingCart(productList);

        BigDecimal result =
                service.calculateTotalPriceWithCategoryDiscountConfigurableDiscount(shoppingCart, 1.0, 0.9);

        Assertions.assertEquals(result, BigDecimal.valueOf(31.84).setScale(2));
    }


}