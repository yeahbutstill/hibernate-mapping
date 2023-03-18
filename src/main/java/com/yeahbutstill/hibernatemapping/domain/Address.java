package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Address {

    @Size(max = 30)
    @NotBlank
    @NotEmpty
    private String address;

    @NotBlank
    @NotEmpty
    @Size(max = 30)
    private String city;

    @NotBlank
    @NotEmpty
    @Size(max = 30)
    private String state;

    @NotBlank
    @NotEmpty
    @Size(max = 30)
    private String zipCode;
}
