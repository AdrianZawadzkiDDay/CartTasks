package com.nphase.repositories;

import com.nphase.entity.Product;

import java.util.List;

public interface ProductsCartRepository {

    void add(Product product);

    void delete(Product product);

    List<Product> getAll();
}
