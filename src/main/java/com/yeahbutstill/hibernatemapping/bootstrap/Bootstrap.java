package com.yeahbutstill.hibernatemapping.bootstrap;

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

  @Autowired
  public Bootstrap(
      OrderHeaderRepository orderHeaderRepository, BootStrapOrderService bootStrapOrderService) {
    this.orderHeaderRepository = orderHeaderRepository;
    this.bootStrapOrderService = bootStrapOrderService;
  }

  @Override
  public void run(String... args) throws Exception {
    bootStrapOrderService.readOrderData();
  }
}
