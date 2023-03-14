package com.yeahbutstill.hibernatemapping;

import com.yeahbutstill.hibernatemapping.domain.*;
import com.yeahbutstill.hibernatemapping.repositories.CustomerRepository;
import com.yeahbutstill.hibernatemapping.repositories.OrderHeaderRepository;
import com.yeahbutstill.hibernatemapping.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataLoadTest extends AbstractIntegrationTest {

    final String PRODUCT_D1 = "Product 1";
    final String PRODUCT_D2 = "Product 2";
    final String PRODUCT_D3 = "Product 3";
    final String TEST_CUSTOMER = "Test Customer";

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @Order(3)
    void testNPlusOneProblem() {

        Customer customer = customerRepository.findCustomerByCustomerNameIgnoreCase(TEST_CUSTOMER).orElseGet(this::getOrSaveCustomer);

        IntSummaryStatistics totalOrdered = orderHeaderRepository.findAllByCustomer(customer).stream()
                .flatMap(orderHeader -> orderHeader.getOrderLines().stream())
                .collect(Collectors.summarizingInt(OrderLine::getQuantityOrdered));

        Assertions.assertNotNull(totalOrdered);

    }

    @Test
    @Order(2)
    void testLazyVsEager() {

        OrderHeader orderHeader = orderHeaderRepository.getReferenceById(5L);

        Assertions.assertNotNull(orderHeader.getId());
        Assertions.assertNotNull(orderHeader.getCustomer().getCustomerName());

    }


    @Rollback(value = false)
    @Test
    @Order(1)
    void testDataLoader() {

        List<Product> products = loadProducts();
        Customer customer = loadCustomers();

        int ordersToCreate = 100;

        for (int i = 0; i < ordersToCreate; i++) {
            System.out.println("Creating order #: " + i);
            saveOrder(customer, products);
        }

        orderHeaderRepository.flush();
        Assertions.assertNotNull(saveOrder(customer, products));

    }

    private OrderHeader saveOrder(Customer customer, List<Product> products) {
        Random random = new Random();

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        products.forEach(product -> {
            OrderLine orderLine = new OrderLine();
            orderLine.setProduct(product);
            orderLine.setQuantityOrdered(random.nextInt(20));
            //orderHeader.getOrderLines().add(orderLine);
            orderHeader.addOrderLine(orderLine);
        });

        return orderHeaderRepository.save(orderHeader);
    }

    private Customer loadCustomers() {
        return getOrSaveCustomer();
    }

    private Customer getOrSaveCustomer() {
        return customerRepository.findCustomerByCustomerNameIgnoreCase("Test Customer")
                .orElseGet(() -> {
                    Customer c1 = new Customer();
                    c1.setCustomerName("Test Customer");
                    c1.setEmail("test@example.com");
                    Address address = new Address();
                    address.setAddress("123 Main");
                    address.setCity("New Orleans");
                    address.setState("LA");
                    c1.setAddress(address);
                    return customerRepository.save(c1);
                });
    }

    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();

        products.add(getOrSaveProduct(PRODUCT_D1));
        products.add(getOrSaveProduct(PRODUCT_D2));
        products.add(getOrSaveProduct(PRODUCT_D3));

        return products;
    }

    private Product getOrSaveProduct(String description) {
        return productRepository.findByDescription(description)
                .orElseGet(() -> {
                    Product p1 = new Product();
                    p1.setDescription(description);
                    p1.setProductStatus(ProductStatus.NEW);
                    return productRepository.save(p1);
                });
    }

}