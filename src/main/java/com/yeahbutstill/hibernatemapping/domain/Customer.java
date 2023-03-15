package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer extends BaseEntity {

  private String customerName;

  @Embedded private Address address;

  private String phone;
  private String email;

  @Version private Integer version;

  @OneToMany(mappedBy = "customer")
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

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "("
        + "id = "
        + getId()
        + ", "
        + "createdDate = "
        + getCreatedDate()
        + ", "
        + "lastModifiedDate = "
        + getLastModifiedDate()
        + ", "
        + "customerName = "
        + getCustomerName()
        + ", "
        + "address = "
        + getAddress()
        + ", "
        + "phone = "
        + getPhone()
        + ", "
        + "email = "
        + getEmail()
        + ")";
  }
}
