package com.yeahbutstill.hibernatemapping.repositories;

import com.yeahbutstill.hibernatemapping.domain.Product;
import com.yeahbutstill.hibernatemapping.domain.ProductStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ProductRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> pgsql = new PostgreSQLContainer<>("postgres:14");

    @Autowired
    public ProductRepository productRepository;

    @DynamicPropertySource
    public static void configureTestContainerProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", pgsql::getJdbcUrl);
        registry.add("spring.datasource.username", pgsql::getUsername);
        registry.add("spring.datasource.password", pgsql::getPassword);

    }

    @Test
    void testSaveProduct() {

        Product newProduct = new Product();
        newProduct.setDescription("New Product");
        newProduct.setProductStatus(ProductStatus.NEW);

        Product savedProduct = productRepository.save(newProduct);
        Product fetchedProduct = productRepository.getReferenceById(savedProduct.getId());

        Assertions.assertNotNull(fetchedProduct);
        Assertions.assertNotNull(fetchedProduct.getDescription());
        Assertions.assertNotNull(fetchedProduct.getProductStatus());
        Assertions.assertNotNull(fetchedProduct.getCreatedDate());
        Assertions.assertNotNull(fetchedProduct.getLastModifiedDate());

    }

}