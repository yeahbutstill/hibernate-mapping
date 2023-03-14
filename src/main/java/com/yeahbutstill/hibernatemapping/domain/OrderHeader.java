package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@AttributeOverride(name = "shippingAddress.address", column = @Column(name = "shipping_address"))
@AttributeOverride(name = "shippingAddress.city", column = @Column(name = "shipping_city"))
@AttributeOverride(name = "shippingAddress.state", column = @Column(name = "shipping_state"))
@AttributeOverride(name = "shippingAddress.zipCode", column = @Column(name = "shipping_zip_code"))
@AttributeOverride(name = "billToAddress.address", column = @Column(name = "bill_to_address"))
@AttributeOverride(name = "billToAddress.city", column = @Column(name = "bill_to_city"))
@AttributeOverride(name = "billToAddress.state", column = @Column(name = "bill_to_state"))
@AttributeOverride(name = "billToAddress.zipCode", column = @Column(name = "bill_to_zip_code"))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderHeader extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Customer customer;

    @Embedded
    private Address shippingAddress;

    @Embedded
    private Address billToAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orderHeader", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    private Set<OrderLine> orderLines;

    @OneToOne(mappedBy = "orderHeader", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private OrderApproval orderApproval;

    public void addOrderLine(OrderLine orderLine) {
        if (orderLines == null) {
            orderLines = new HashSet<>();
        }

        orderLines.add(orderLine);
        orderLine.setOrderHeader(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        OrderHeader that = (OrderHeader) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
