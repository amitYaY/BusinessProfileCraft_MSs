package com.craft.kafka.consumer;

import com.craft.constant.BusinessProfileStatus;
import com.craft.dao.BusinessProfileRepository;
import com.craft.domain.BusinessProfileModel;
import com.craft.exception.BusinessException;
import com.craft.service.BusinessProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BusinessProfileValidatorKafkaConsumer {

    private final BusinessProfileService businessProfileService;

    private final BusinessProfileRepository businessProfileRepository;

    @Autowired
    public BusinessProfileValidatorKafkaConsumer(BusinessProfileService businessProfileService, BusinessProfileRepository businessProfileRepository) {
        this.businessProfileService = businessProfileService;
        this.businessProfileRepository = businessProfileRepository;
    }

    @KafkaListener(containerFactory = "kafkaListenerContainerFactory", groupId = "business_profile_validation_group", topics = "businessprofile")
    public void validateBusinessProfileUpdate(BusinessProfileModel profileModel) throws BusinessException {

        Boolean validationRes = businessProfileService.validateBusinessProfile(profileModel);
        log.info("[validateBusinessProfileUpdate] validateBusinessProfileUpdate Response: " + validationRes);
        if (validationRes) {
            profileModel.setStatus(BusinessProfileStatus.ACCEPTED);
        } else {
            profileModel.setStatus(BusinessProfileStatus.REJECTED);
        }
        businessProfileRepository.save(profileModel);
    }

}