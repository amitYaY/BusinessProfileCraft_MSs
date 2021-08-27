package com.craft.dto;

import com.craft.domain.Address;
import com.craft.domain.TaxIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BusinessProfileDTO {

    @NotNull(message = "Id is Required Field")
    private String id;

    private String companyName;

    private String legalName;

    private Address address;

    private String legalAddress;

    private TaxIdentifier taxIdentifier;

    @NotNull(message = "Email Id is Required Field")
    private String email;

    private String website;

}
