package com.craft.service;

import com.craft.domain.BusinessProfileModel;
import com.craft.dto.BusinessProfileDTO;
import com.craft.exception.BusinessException;

public interface BusinessProfileService {

    BusinessProfileModel createBusinessProfile(BusinessProfileDTO businessProfile) throws BusinessException;

    BusinessProfileModel updateBusinessProfile(BusinessProfileDTO businessProfile, String id) throws BusinessException;

    String deleteBusinessProfile(String id);

    BusinessProfileModel getBusinessProfileById(String id) throws BusinessException;

    Boolean validateBusinessProfile(BusinessProfileModel model) throws BusinessException;

}
