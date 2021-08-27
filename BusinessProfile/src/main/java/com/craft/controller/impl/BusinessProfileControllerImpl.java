package com.craft.controller.impl;

import com.craft.common.Response;
import com.craft.constant.BusinessProfileStatus;
import com.craft.controller.BusinessProfileController;
import com.craft.domain.BusinessProfileModel;
import com.craft.dto.BusinessProfileDTO;
import com.craft.exception.BusinessException;
import com.craft.exception.Error;
import com.craft.exception.InvalidInputException;
import com.craft.service.BusinessProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Component
public class BusinessProfileControllerImpl implements BusinessProfileController {

    private final BusinessProfileService businessProfileService;

    @Autowired
    public BusinessProfileControllerImpl(BusinessProfileService businessProfileService) {
        this.businessProfileService = businessProfileService;
    }

    @Override
    public Response<BusinessProfileModel> createBusinessProfile(BusinessProfileDTO businessProfile) throws BusinessException {
        log.info(">> [createBusinessProfile] called.");
        Response<BusinessProfileModel> modelResponse = new Response<>();
        BusinessProfileModel profileModel = businessProfileService.createBusinessProfile(businessProfile);
        modelResponse.setStatus(HttpStatus.OK.name());
        modelResponse.setPayload(profileModel);
        log.info("<< [createBusinessProfile] Exit.");
        return modelResponse;
    }

    @Override
    public Response<BusinessProfileModel> updateBusinessProfile(BusinessProfileDTO businessProfile, String id) throws BusinessException {
        log.info(">> [updateBusinessProfile] called.");
        Response<BusinessProfileModel> modelResponse = new Response<>();
        BusinessProfileModel profileModel = businessProfileService.updateBusinessProfile(businessProfile, id);
        modelResponse.setStatus(HttpStatus.OK.name());
        modelResponse.setPayload(profileModel);
        log.info("<< [updateBusinessProfile] Exit.");
        return modelResponse;
    }

    @Override
    public Response<String> deleteBusinessProfile(String id) {
        log.info(">> [deleteBusinessProfile] called.");
        Response<String> modelResponse = new Response<>();
        String removedId = businessProfileService.deleteBusinessProfile(id);
        modelResponse.setStatus(HttpStatus.OK.name());
        modelResponse.setPayload(removedId);
        log.info("<< [deleteBusinessProfile] Exit.");
        return modelResponse;
    }

    @Override
    public Response<BusinessProfileModel> getActiveBusinessProfileById(@RequestParam(name = "id", required = true) String id) throws BusinessException {
        log.info(">> [getActiveBusinessProfileById] called.");
        Response<BusinessProfileModel> modelResponse = new Response<>();
        BusinessProfileModel profileModel = businessProfileService.getBusinessProfileById(id);
        try {
            if (!BusinessProfileStatus.ACCEPTED.equals(profileModel.getStatus())) {
                log.error("Business Profile Not found for Id {}", id);
                throw new InvalidInputException("Business Profile Not found for Id: " + id);
            }
            modelResponse.setStatus(HttpStatus.OK.name());
            modelResponse.setPayload(profileModel);
        } catch (Exception ex) {
            Error error = new Error(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), ex.getMessage());
            throw new BusinessException(HttpStatus.valueOf(error.getCode()).value(), error, ex.getMessage(), ex);
        }
        log.info("<< [getActiveBusinessProfileById] Exit.");
        return modelResponse;
    }

    @Override
    public Response<BusinessProfileModel> getBusinessProfileById(String id) throws BusinessException {
        log.info(">> [getBusinessProfileById] called.");
        Response<BusinessProfileModel> modelResponse = new Response<>();
        BusinessProfileModel profileModel = businessProfileService.getBusinessProfileById(id);
        modelResponse.setStatus(HttpStatus.OK.name());
        modelResponse.setPayload(profileModel);
        log.info("<< [getBusinessProfileById] Exit.");
        return modelResponse;
    }

    @Override
    public Response<String> getBusinessProfileStatus(String id) throws BusinessException {
        log.info(">> [getBusinessProfileStatus] called.");
        Response<String> modelResponse = new Response<>();
        BusinessProfileModel profileModel = businessProfileService.getBusinessProfileById(id);
        String status = profileModel.getStatus().name();
        modelResponse.setStatus(HttpStatus.OK.name());
        modelResponse.setPayload(status);
        log.info("<< [getBusinessProfileStatus] Exit.");
        return modelResponse;
    }

}
