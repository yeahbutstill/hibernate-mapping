package com.yeahbutstill.hibernatemapping.repositories;

import com.yeahbutstill.hibernatemapping.domain.Customer;
import com.yeahbutstill.hibernatemapping.domain.OrderHeader;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
  List<OrderHeader> findAllByCustomer(Customer customer);
}
