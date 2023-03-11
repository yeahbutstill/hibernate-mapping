package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Address {

  private String address;
  private String city;
  private String state;
  private String zipCode;


}
