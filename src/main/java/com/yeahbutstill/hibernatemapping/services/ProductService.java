package com.yeahbutstill.hibernatemapping.services;

import com.yeahbutstill.hibernatemapping.domain.Product;

public interface ProductService {
  Product savedProduct(Product product);

  Product updateQOH(Long id, Integer quantityOnHand);
}
