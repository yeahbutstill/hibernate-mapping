package com.yeahbutstill.hibernatemapping.bootstrap;

import com.yeahbutstill.hibernatemapping.domain.Customer;
import com.yeahbutstill.hibernatemapping.domain.Product;
import com.yeahbutstill.hibernatemapping.domain.ProductStatus;
import com.yeahbutstill.hibernatemapping.repositories.CustomerRepository;
import com.yeahbutstill.hibernatemapping.repositories.OrderHeaderRepository;
import com.yeahbutstill.hibernatemapping.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

  private final OrderHeaderRepository orderHeaderRepository;
  private final BootStrapOrderService bootStrapOrderService;
  private final CustomerRepository customerRepository;
  private final ProductService productService;

  @Autowired
  public Bootstrap(
      OrderHeaderRepository orderHeaderRepository,
      BootStrapOrderService bootStrapOrderService,
      CustomerRepository customerRepository,
      ProductService productService) {
    this.orderHeaderRepository = orderHeaderRepository;
    this.bootStrapOrderService = bootStrapOrderService;
    this.customerRepository = customerRepository;
    this.productService = productService;
  }

  private void updateProduct() {
    Product product = new Product();
    product.setDescription("NTT Ler");
    product.setProductStatus(ProductStatus.NEW);

    Product savedProduct = productService.savedProduct(product);
    Product savedProduct1 = productService.updateQOH(savedProduct.getId(), 25);
    log.info("Update Qty: {}", savedProduct1.getQuantityOnHand());
  }

  @Override
  public void run(String... args) throws Exception {
    updateProduct();
    bootStrapOrderService.readOrderData();

    Customer customer = new Customer();
    customer.setCustomerName("Testing version");
    Customer savedCustomer = customerRepository.save(customer);
    log.info("Version is - {}", savedCustomer.getVersion());

    savedCustomer.setCustomerName("Testing version 2");
    Customer savedCustomer1 = customerRepository.save(savedCustomer);
    log.info("Version is - {}", savedCustomer1.getVersion());

    savedCustomer1.setCustomerName("Testing version 3");
    Customer savedCustomer2 = customerRepository.save(savedCustomer1);
    log.info("Version is - {}", savedCustomer2.getVersion());

    customerRepository.delete(savedCustomer2);
  }
}
