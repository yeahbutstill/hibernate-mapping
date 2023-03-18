package com.yeahbutstill.hibernatemapping.services;

import com.yeahbutstill.hibernatemapping.domain.Product;

import java.util.List;

public interface ProductService {
    Product savedProduct(Product product);

    Product updateQOH(Long id, Integer quantityOnHand);

    List<Product> getAllProducts();

    Product getProductById(Long productId);
}
