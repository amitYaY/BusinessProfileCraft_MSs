package com.craft.domain;

import com.craft.constant.BusinessProfileStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "BusinessProfiles")
public class BusinessProfileModel {

    @Id
    private String id;

    private String companyName;

    private String legalName;

    private Address address;

    private String legalAddress;

    private TaxIdentifier taxIdentifier;

    private String email;

    private String website;

    private BusinessProfileStatus status = BusinessProfileStatus.IN_PROGRESS;

}
