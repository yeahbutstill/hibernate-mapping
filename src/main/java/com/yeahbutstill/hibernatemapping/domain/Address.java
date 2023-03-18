package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Address {

    @Length(max = 30)
    @NotBlank
    @NotEmpty
    private String address;

    @NotBlank
    @NotEmpty
    @Length(max = 30)
    private String city;

    @NotBlank
    @NotEmpty
    @Length(max = 30)
    private String state;

    @NotBlank
    @NotEmpty
    @Length(max = 30)
    private String zipCode;
}
