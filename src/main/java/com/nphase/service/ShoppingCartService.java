package com.nphase.service;

import com.nphase.common.Category;
import com.nphase.common.ProductsCategoryInfo;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.repositories.ProductsCartRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ShoppingCartService {

    private final ProductsCartRepository productsCartRepository;

    public ShoppingCartService(ProductsCartRepository productsCartRepository) {
        this.productsCartRepository = productsCartRepository;
    }

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal calculateTotalPriceWithDiscount(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(product -> product.getPricePerUnit()
                        .multiply(product.getQuantity() > 3 ?
                                BigDecimal.valueOf(product.getQuantity() * 0.9).setScale(2, RoundingMode.HALF_UP) :
                                BigDecimal.valueOf(product.getQuantity())).setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal calculateTotalPriceWithCategoryDiscount(ShoppingCart shoppingCart) {
        List<Product> productList = new ArrayList<>(shoppingCart.getProducts());

        Set<Category> categoriesSet = productList.stream().map(Product::getCategory).collect(Collectors.toSet());

        Map<Category, Boolean> categoryDiscountMap = categoriesSet.stream()
                        .map(category -> {
                            int amount = productList.stream().filter(p -> p.getCategory() == category)
                                    .map(Product::getQuantity)
                                    .reduce(0, (a, b) -> a + b);

                            boolean isCategoryDiscount = amount > 3;
                            return new ProductsCategoryInfo(category, isCategoryDiscount);
                        })
                .collect(Collectors.toMap(ProductsCategoryInfo::getCategory, ProductsCategoryInfo::isCategoryDiscount));

        return productList
                .stream()
                .map(product -> {
                    if(categoryDiscountMap.get(product.getCategory())) {
                        product.setTotalPrice(product.getTotalPrice().multiply(BigDecimal.valueOf(0.9)).setScale(2, RoundingMode.HALF_UP));
                        return product;
                    }
                    return product;
                })
                .map(product -> {
                    if(product.getQuantity() > 3) {
                        return BigDecimal.valueOf(product.getQuantity() * 0.9).setScale(2, RoundingMode.HALF_UP);
                    }
                    return product.getTotalPrice();})
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void shoppingCartConfigure() {

    }

    public BigDecimal calculateTotalPriceWithCategoryDiscountConfigurableDiscount(ShoppingCart shoppingCart, double quantityDiscount, double categoryDiscount) {
        List<Product> productList = new ArrayList<>(shoppingCart.getProducts());

        Set<Category> categoriesSet = productList.stream().map(Product::getCategory).collect(Collectors.toSet());

        Map<Category, Boolean> categoryDiscountMap = categoriesSet.stream()
                .map(category -> {
                    int amount = productList.stream().filter(p -> p.getCategory() == category)
                            .map(Product::getQuantity)
                            .reduce(0, (a, b) -> a + b);

                    boolean isCategoryDiscount = amount > 3;
                    return new ProductsCategoryInfo(category, isCategoryDiscount);
                })
                .collect(Collectors.toMap(ProductsCategoryInfo::getCategory, ProductsCategoryInfo::isCategoryDiscount));

        return productList
                .stream()
                .map(product -> {
                    if(categoryDiscountMap.get(product.getCategory())) {
                        product.setTotalPrice(product.getTotalPrice().multiply(BigDecimal.valueOf(0.9)).setScale(2, RoundingMode.HALF_UP));
                        return product;
                    }
                    return product;
                })
                .map(product -> {
                    if(product.getQuantity() > 3) {
                        return BigDecimal.valueOf(product.getQuantity() * 0.9).setScale(2, RoundingMode.HALF_UP);
                    }
                    return product.getTotalPrice();})
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}
