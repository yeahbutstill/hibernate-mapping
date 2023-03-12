package com.yeahbutstill.hibernatemapping.repositories;

import com.yeahbutstill.hibernatemapping.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
