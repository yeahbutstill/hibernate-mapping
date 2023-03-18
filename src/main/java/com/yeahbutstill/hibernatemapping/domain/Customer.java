package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer extends BaseEntity {

    @Length(max = 50)
    @NotBlank
    @NotEmpty
    private String customerName;

    @Valid
    @Embedded
    private Address address;

    @NotEmpty
    @NotBlank
    @Length(max = 20)
    private String phone;

    @NotEmpty
    @NotBlank
    @Email
    @Length(max = 255)
    private String email;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private Set<OrderHeader> orders = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
