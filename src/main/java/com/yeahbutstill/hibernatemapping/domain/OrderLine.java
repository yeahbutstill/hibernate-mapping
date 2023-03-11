package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrderLine extends BaseEntity {

    private Integer quantityOrdered;

    @ManyToOne
    private OrderHeader orderHeader;


}
