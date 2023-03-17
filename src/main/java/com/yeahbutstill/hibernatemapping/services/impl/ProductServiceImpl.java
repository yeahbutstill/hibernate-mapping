package com.yeahbutstill.hibernatemapping.services.impl;

import com.yeahbutstill.hibernatemapping.domain.Product;
import com.yeahbutstill.hibernatemapping.repositories.ProductRepository;
import com.yeahbutstill.hibernatemapping.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product savedProduct(Product product) {
    return productRepository.saveAndFlush(product);
  }

  @Transactional
  @Override
  public Product updateQOH(Long id, Integer quantityOnHand) {
    Product product = productRepository.findById(id).orElseThrow();
    product.setQuantityOnHand(quantityOnHand);

    return productRepository.saveAndFlush(product);
  }
}
