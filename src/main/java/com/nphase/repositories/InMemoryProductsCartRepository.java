package com.nphase.repositories;

import com.nphase.entity.Product;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProductsCartRepository implements ProductsCartRepository {
    Map<String, Product> products = new HashMap<>();

    @Override
    public void add(Product product) {
        if(products.containsKey(product.getName())) {
           Product oldProduct = products.get(product.getName());
           Product productToAdd = oldProduct.setQuantity(oldProduct.getQuantity() + product.getQuantity());

           products.put(product.getName(), productToAdd);

        } else {
            products.put(product.getName(), product);
        }

    }

    @Override
    public void delete(Product product) {
        if(products.containsKey(product.getName())) {
            Product oldProduct = products.get(product.getName());
            if(oldProduct.getQuantity() > 0) {
                Product productToAdd = oldProduct.setQuantity(oldProduct.getQuantity() - product.getQuantity());
                products.put(product.getName(), productToAdd);
            } else {
                products.remove(product.getName());
            }
        }
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(products.values());
    }

    public Map<String, Product> getAllMap() {
        return products;
    }
}
