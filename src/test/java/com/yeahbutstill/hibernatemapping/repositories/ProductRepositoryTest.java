package com.yeahbutstill.hibernatemapping.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.yeahbutstill.hibernatemapping.AbstractIntegrationTest;
import com.yeahbutstill.hibernatemapping.domain.Product;
import com.yeahbutstill.hibernatemapping.domain.ProductStatus;
import com.yeahbutstill.hibernatemapping.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {ProductService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest extends AbstractIntegrationTest {

  @Autowired ProductRepository productRepository;

  @Autowired ProductService productService;

  @Test
  void testGetCategory() {
    Product product = productRepository.findByDescription("PRODUCT1").get();

    assertNotNull(product);
    assertNotNull(product.getCategories());
  }

  @Test
  void testSaveProduct() {
    Product product = new Product();
    product.setDescription("My Product");
    product.setProductStatus(ProductStatus.NEW);

    Product savedProduct = productRepository.save(product);

    Product fetchedProduct = productRepository.getReferenceById(savedProduct.getId());

    assertNotNull(fetchedProduct);
    assertNotNull(fetchedProduct.getDescription());
    assertNotNull(fetchedProduct.getCreatedDate());
    assertNotNull(fetchedProduct.getLastModifiedDate());
  }

  @Test
  void addAndUpdateProduct() {
    Product product = new Product();
    product.setDescription("My Product");
    product.setProductStatus(ProductStatus.NEW);

    Product savedProduct = productService.savedProduct(product);

    Product savedProduct1 = productService.updateQOH(savedProduct.getId(), 25);

    Assertions.assertNotNull(savedProduct1.getQuantityOnHand());
  }
}
