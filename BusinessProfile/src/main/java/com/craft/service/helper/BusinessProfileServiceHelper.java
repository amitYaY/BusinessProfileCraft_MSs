package com.craft.service.helper;

import com.craft.domain.Address;
import com.craft.domain.BusinessProfileModel;
import com.craft.domain.TaxIdentifier;
import com.craft.dto.BusinessProfileDTO;

public class BusinessProfileServiceHelper {

    public static void updatedBusinessProfileUpdateModel(BusinessProfileModel exitingModel, BusinessProfileDTO updatedBusinessProfile) {

        if (updatedBusinessProfile != null) {
            if (updatedBusinessProfile.getCompanyName() == null) {
                updatedBusinessProfile.setCompanyName(exitingModel.getCompanyName());
            }
            if (updatedBusinessProfile.getEmail() == null) {
                updatedBusinessProfile.setEmail(exitingModel.getEmail());
            }
            if (updatedBusinessProfile.getLegalAddress() == null) {
                updatedBusinessProfile.setLegalAddress(exitingModel.getLegalAddress());
            }
            if (updatedBusinessProfile.getWebsite() == null) {
                updatedBusinessProfile.setWebsite(exitingModel.getWebsite());
            }
            if (updatedBusinessProfile.getLegalName() == null) {
                updatedBusinessProfile.setLegalName(exitingModel.getLegalName());
            }
            if (updatedBusinessProfile.getAddress() == null) {
                updatedBusinessProfile.setAddress(exitingModel.getAddress());
            } else if (exitingModel.getAddress() != null) {
                Address address = updatedBusinessProfile.getAddress();
                if (address.getAddressLine1() == null) {
                    address.setAddressLine1(exitingModel.getAddress().getAddressLine1());
                }
                if (address.getAddressLine2() == null) {
                    address.setAddressLine2(exitingModel.getAddress().getAddressLine2());
                }
                if (address.getZipCode() == null) {
                    address.setZipCode(exitingModel.getAddress().getZipCode());
                }
                if (address.getCity() == null) {
                    address.setCity(exitingModel.getAddress().getCity());
                }
                if (address.getState() == null) {
                    address.setState(exitingModel.getAddress().getState());
                }
                if (address.getCountry() == null) {
                    address.setCountry(exitingModel.getAddress().getCountry());
                }
            }
            if (updatedBusinessProfile.getTaxIdentifier() == null) {
                updatedBusinessProfile.setTaxIdentifier(exitingModel.getTaxIdentifier());
            } else if (exitingModel.getTaxIdentifier() != null) {
                TaxIdentifier taxIdentifier = updatedBusinessProfile.getTaxIdentifier();
                if (taxIdentifier.getPan() == null) {
                    taxIdentifier.setPan(exitingModel.getTaxIdentifier().getPan());
                }
                if (taxIdentifier.getEin() == null) {
                    taxIdentifier.setEin(exitingModel.getTaxIdentifier().getEin());
                }
            }
        }
    }

}
