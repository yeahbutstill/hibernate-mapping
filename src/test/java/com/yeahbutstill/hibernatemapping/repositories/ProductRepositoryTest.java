package com.yeahbutstill.hibernatemapping.repositories;

import com.yeahbutstill.hibernatemapping.AbstractIntegrationTest;
import com.yeahbutstill.hibernatemapping.domain.Product;
import com.yeahbutstill.hibernatemapping.domain.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class ProductRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    public ProductRepository productRepository;

    @Test
    void testGetCategory() {

        Product product1 = productRepository.findByDescription("PRODUCT1");
        assertNotNull(product1);
        assertNotNull(product1.getDescription());

    }

    @Test
    void testSaveProduct() {

        Product newProduct = new Product();
        newProduct.setDescription("New Product");
        newProduct.setProductStatus(ProductStatus.NEW);

        Product savedProduct = productRepository.save(newProduct);
        Product fetchedProduct = productRepository.getReferenceById(savedProduct.getId());

        assertNotNull(fetchedProduct);
        assertNotNull(fetchedProduct.getDescription());
        assertNotNull(fetchedProduct.getProductStatus());
        assertNotNull(fetchedProduct.getCreatedDate());
        assertNotNull(fetchedProduct.getLastModifiedDate());

    }

}