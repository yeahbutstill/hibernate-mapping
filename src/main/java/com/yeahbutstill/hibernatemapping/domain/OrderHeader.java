package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "shippingAddress.address", column = @Column(name = "shipping_address")),
        @AttributeOverride(name = "shippingAddress.city", column = @Column(name = "shipping_city")),
        @AttributeOverride(name = "shippingAddress.state", column = @Column(name = "shipping_state")),
        @AttributeOverride(
                name = "shippingAddress.zipCode",
                column = @Column(name = "shipping_zip_code")),
        @AttributeOverride(name = "billToAddress.address", column = @Column(name = "bill_to_address")),
        @AttributeOverride(name = "billToAddress.city", column = @Column(name = "bill_to_city")),
        @AttributeOverride(name = "billToAddress.state", column = @Column(name = "bill_to_state")),
        @AttributeOverride(name = "billToAddress.zipCode", column = @Column(name = "bill_to_zip_code"))
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrderHeader extends BaseEntity {

    private String customer;

    @Embedded
    private Address shippingAddress;

    @Embedded
    private Address billToAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orderHeader")
    private Set<OrderLine> orderLines;

}
