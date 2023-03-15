package com.yeahbutstill.hibernatemapping.bootstrap;

import com.yeahbutstill.hibernatemapping.domain.Customer;
import com.yeahbutstill.hibernatemapping.repositories.CustomerRepository;
import com.yeahbutstill.hibernatemapping.repositories.OrderHeaderRepository;
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

  @Autowired
  public Bootstrap(
      OrderHeaderRepository orderHeaderRepository,
      BootStrapOrderService bootStrapOrderService,
      CustomerRepository customerRepository) {
    this.orderHeaderRepository = orderHeaderRepository;
    this.bootStrapOrderService = bootStrapOrderService;
    this.customerRepository = customerRepository;
  }

  @Override
  public void run(String... args) throws Exception {
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
