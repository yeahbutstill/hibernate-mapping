package com.yeahbutstill.hibernatemapping.repositories;

import com.yeahbutstill.hibernatemapping.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}
