package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.*;
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
public class Product extends BaseEntity {
  private String description;

  @Enumerated(EnumType.STRING)
  private ProductStatus productStatus;

  @ManyToMany
  @JoinTable(
      name = "product_category",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Product product = (Product) o;
    return getId() != null && Objects.equals(getId(), product.getId());
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
        + "description = "
        + getDescription()
        + ", "
        + "productStatus = "
        + getProductStatus()
        + ")";
  }
}
