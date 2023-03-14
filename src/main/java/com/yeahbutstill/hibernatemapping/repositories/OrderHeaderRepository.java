package com.yeahbutstill.hibernatemapping.repositories;

import com.yeahbutstill.hibernatemapping.domain.Customer;
import com.yeahbutstill.hibernatemapping.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
    List<OrderHeader> findAllByCustomer(Customer customer);
}
