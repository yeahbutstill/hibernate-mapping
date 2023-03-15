package com.yeahbutstill.hibernatemapping;

import com.yeahbutstill.hibernatemapping.domain.*;
import com.yeahbutstill.hibernatemapping.repositories.CustomerRepository;
import com.yeahbutstill.hibernatemapping.repositories.OrderHeaderRepository;
import com.yeahbutstill.hibernatemapping.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.event.EventListener;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DataLocalLoadTest {

  final String PRODUCT_D1 = "Product 1";
  final String PRODUCT_D2 = "Product 2";
  final String PRODUCT_D3 = "Product 3";
  final String TEST_CUSTOMER = "Test Customer";

  @Autowired OrderHeaderRepository orderHeaderRepository;

  @Autowired CustomerRepository customerRepository;

  @Autowired ProductRepository productRepository;

  @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
  private int batchSize;

  @Test
  void testDBLock() {

    Long id = 1L;

    OrderHeader orderHeader = orderHeaderRepository.findById(id).orElseGet(OrderHeader::new);

    Address billTo = new Address();
    billTo.setAddress("Bill me");
    orderHeader.setBillToAddress(billTo);
    orderHeaderRepository.saveAndFlush(orderHeader);

    log.info("I updated the order");
  }

  @Test
  @Order(3)
  void testNPlusOneProblem() {

    Customer customer =
        customerRepository
            .findCustomerByCustomerNameIgnoreCase(TEST_CUSTOMER)
            .orElseGet(this::getOrSaveCustomer);

    IntSummaryStatistics totalOrdered =
        orderHeaderRepository.findAllByCustomer(customer).stream()
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

  @EventListener(ApplicationReadyEvent.class)
  @Rollback(value = false)
  @Test
  @Order(1)
  void testDataLoader() {

    List<Product> products = loadProducts();
    Customer customer = loadCustomers();

    int ordersToCreate = 10_000;
    long start = System.currentTimeMillis();
    log.info(
        "Finished creating "
            + ordersToCreate
            + " objects in memory in:"
            + (System.currentTimeMillis() - start) / 1000);

    start = System.currentTimeMillis();
    log.info("Inserting ..........");

    for (int i = 0; i < ordersToCreate; i += batchSize) {
      if (i + batchSize > ordersToCreate) {
        saveOrder(customer, products);
        break;
      }
      saveOrder(customer, products);
      orderHeaderRepository.flush();
    }

    log.info(
        "Finished inserting "
            + ordersToCreate
            + " objects in :"
            + (System.currentTimeMillis() - start));

    Assertions.assertNotNull(saveOrder(customer, products));
  }

  private OrderHeader saveOrder(Customer customer, List<Product> products) {
    Random random = new Random();

    OrderHeader orderHeader = new OrderHeader();
    orderHeader.setCustomer(customer);

    products.forEach(
        product -> {
          OrderLine orderLine = new OrderLine();
          orderLine.setProduct(product);
          orderLine.setQuantityOrdered(random.nextInt(20));
          // orderHeader.getOrderLines().add(orderLine);
          orderHeader.addOrderLine(orderLine);
        });

    return orderHeaderRepository.save(orderHeader);
  }

  private Customer loadCustomers() {
    return getOrSaveCustomer();
  }

  private Customer getOrSaveCustomer() {
    return customerRepository
        .findCustomerByCustomerNameIgnoreCase("Test Customer")
        .orElseGet(
            () -> {
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
    return productRepository
        .findByDescription(description)
        .orElseGet(
            () -> {
              Product p1 = new Product();
              p1.setDescription(description);
              p1.setProductStatus(ProductStatus.NEW);
              return productRepository.save(p1);
            });
  }
}
