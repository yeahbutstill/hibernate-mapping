package com.yeahbutstill.hibernatemapping.controller;

import com.yeahbutstill.hibernatemapping.domain.OrderHeader;
import com.yeahbutstill.hibernatemapping.repositories.OrderHeaderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OrderRestController {

    private final OrderHeaderRepository repository;

    OrderRestController(OrderHeaderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders")
    Iterable<OrderHeader> get() {
        return repository.findAll();
    }
}
