package com.yeahbutstill.hibernatemapping.bootstrap;

import com.yeahbutstill.hibernatemapping.domain.OrderHeader;
import com.yeahbutstill.hibernatemapping.repositories.OrderHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BootStrapOrderService {

    private final OrderHeaderRepository orderHeaderRepository;

    @Autowired
    public BootStrapOrderService(OrderHeaderRepository orderHeaderRepository) {
        this.orderHeaderRepository = orderHeaderRepository;
    }

    @Transactional
    public void readOrderData() {
        OrderHeader orderHeader = orderHeaderRepository.findById(1L).orElseGet(OrderHeader::new);
        orderHeader
                .getOrderLines()
                .forEach(
                        ol -> {
                            log.info("Data - {}", ol.getProduct().getDescription());
                            ol.getProduct()
                                    .getCategories()
                                    .forEach(
                                            cat -> {
                                                log.info(cat.getDescription());
                                            });
                        });
    }
}
