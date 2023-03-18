package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderLine extends BaseEntity {

    @NotNull
    @Min(1)
    private Integer quantityOrdered;

    @Version
    private Integer version;

    @ManyToOne
    private OrderHeader orderHeader;

    @ManyToOne
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        OrderLine orderLine = (OrderLine) o;
        return getId() != null && Objects.equals(getId(), orderLine.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
