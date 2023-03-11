package com.yeahbutstill.hibernatemapping.repositories;

import com.yeahbutstill.hibernatemapping.domain.OrderHeader;
import com.yeahbutstill.hibernatemapping.domain.OrderLine;
import com.yeahbutstill.hibernatemapping.domain.Product;
import com.yeahbutstill.hibernatemapping.domain.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class OrderHeaderRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> pgsql = new PostgreSQLContainer<>("postgres:14");

    @Autowired
    public OrderHeaderRepository orderHeaderRepository;

    @Autowired
    public ProductRepository productRepository;

    public Product product;

    @DynamicPropertySource
    public static void configureTestContainerProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", pgsql::getJdbcUrl);
        registry.add("spring.datasource.username", pgsql::getUsername);
        registry.add("spring.datasource.password", pgsql::getPassword);
    }

    @BeforeEach
    void setup() {

        Product newProduct1 = new Product();
        newProduct1.setProductStatus(ProductStatus.NEW);
        newProduct1.setDescription("Test dede mario bros product");

        product = productRepository.saveAndFlush(newProduct1);

    }

    @Test
    void testSaveOrderWithLine() {

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("Dede mario bros");

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(100);
        orderLine.setProduct(product);

        //orderHeader.setOrderLines(Set.of(orderLine));
        //orderLine.setOrderHeader(orderHeader);

        orderHeader.addOrderLine(orderLine);

        OrderHeader saveOrderByDedeMarioBros = orderHeaderRepository.save(orderHeader);

        orderHeaderRepository.flush();

        assertNotNull(saveOrderByDedeMarioBros);
        assertNotNull(saveOrderByDedeMarioBros.getId());
        assertNotNull(saveOrderByDedeMarioBros.getOrderLines());
        assertEquals(1, saveOrderByDedeMarioBros.getOrderLines().size());

        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(saveOrderByDedeMarioBros.getId());

        assertNotNull(fetchedOrder);
        assertEquals(1, fetchedOrder.getOrderLines().size());

    }

    @Test
    void testSaveOrder() {

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("New Customer");
        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());

    }
}
