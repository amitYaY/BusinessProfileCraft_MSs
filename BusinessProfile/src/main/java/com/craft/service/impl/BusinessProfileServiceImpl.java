package com.craft.service.impl;

import com.craft.configuration.BusinessProfileProperty;
import com.craft.constant.BusinessProfileStatus;
import com.craft.dao.BusinessProfileRepository;
import com.craft.domain.BusinessProfileModel;
import com.craft.dto.BusinessProfileDTO;
import com.craft.exception.BusinessException;
import com.craft.exception.Error;
import com.craft.exception.InvalidInputException;
import com.craft.integration.ValidatorIntegrationService;
import com.craft.kafka.producer.BusinessProfileKafkaProducer;
import com.craft.service.BusinessProfileService;
import com.craft.service.helper.BusinessProfileServiceHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BusinessProfileServiceImpl implements BusinessProfileService {

    private final BusinessProfileRepository businessProfileRepository;

    private final BusinessProfileProperty businessProfileProperty;

    private final ValidatorIntegrationService validatorIntegrationService;

    private final BusinessProfileKafkaProducer businessProfileKafkaProducer;

    private ThreadPoolExecutor threadPoolExecutor;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public BusinessProfileServiceImpl(BusinessProfileRepository businessProfileRepository, ValidatorIntegrationService validatorIntegrationService,
                                      BusinessProfileProperty businessProfileProperty, BusinessProfileKafkaProducer businessProfileKafkaProducer) {
        this.businessProfileRepository = businessProfileRepository;
        this.validatorIntegrationService = validatorIntegrationService;
        this.businessProfileProperty = businessProfileProperty;
        this.businessProfileKafkaProducer = businessProfileKafkaProducer;
    }

    @PostConstruct
    public void init() {
        int corePoolSize = businessProfileProperty.getCorePoolSize();
        int maxPoolSize = businessProfileProperty.getMaxPoolSize();
        int queueCapacity = businessProfileProperty.getQueueCapacity();

        BlockingQueue<Runnable> poolQueue = new LinkedBlockingQueue<>(queueCapacity);
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0, TimeUnit.SECONDS, poolQueue);
    }

    @PreDestroy
    public void destroy() {
        threadPoolExecutor.shutdown();
    }

    @Override
    @CachePut(value = {"business_profile"}, keyGenerator = "businessProfileCacheKeyGeneratorForCreate")
    public BusinessProfileModel createBusinessProfile(BusinessProfileDTO businessProfile) throws BusinessException {
        log.info(">> [createBusinessProfile] called.");
        BusinessProfileModel profileModel = null;
        try {
            Boolean duplicateRequest = businessProfileRepository.existsById(businessProfile.getId());
            if (duplicateRequest) {
                throw new InvalidInputException("Business Profile Already Exits for Id: " + businessProfile.getId());
            }
            profileModel = mapper.convertValue(businessProfile, BusinessProfileModel.class);
            profileModel = businessProfileRepository.save(profileModel);
            businessProfileKafkaProducer.publishBusinessProfileForValidation(profileModel);
        } catch (Exception ex) {
            Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), ex.getMessage());
            throw new BusinessException(HttpStatus.valueOf(error.getCode()).value(), error, ex.getMessage(), ex);
        }
        log.info("<< [createBusinessProfile] Exit.");
        return profileModel;
    }

    @Override
    @CachePut(value = {"business_profile"}, keyGenerator = "businessProfileCacheKeyGeneratorForPut")
    public BusinessProfileModel updateBusinessProfile(BusinessProfileDTO businessProfile, String id) throws BusinessException {
        log.info(">> [updateBusinessProfile] called.");
        BusinessProfileModel profileModel = null;
        try {
            BusinessProfileModel model = businessProfileRepository.findById(id).orElse(null);
            if (model == null) {
                throw new InvalidInputException("Business Profile Not found for Id: " + id);
            }
            BusinessProfileServiceHelper.updatedBusinessProfileUpdateModel(model, businessProfile);
            profileModel = mapper.convertValue(businessProfile, BusinessProfileModel.class);
            profileModel.setId(id);
            profileModel.setStatus(BusinessProfileStatus.IN_PROGRESS);
            profileModel = businessProfileRepository.save(profileModel);
            businessProfileKafkaProducer.publishBusinessProfileForValidation(profileModel);
            Boolean validationRes = validateBusinessProfile(profileModel);
            log.info("[updateBusinessProfile] validateBusinessProfileUpdate Response: " + validationRes);
            if (validationRes) {
                profileModel.setStatus(BusinessProfileStatus.ACCEPTED);
            } else {
                profileModel.setStatus(BusinessProfileStatus.REJECTED);
            }
            profileModel = businessProfileRepository.save(profileModel);
        } catch (Exception ex) {
            Error error = new Error(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), ex.getMessage());
            throw new BusinessException(HttpStatus.valueOf(error.getCode()).value(), error, ex.getMessage(), ex);
        }
        log.info("<< [updateBusinessProfile] Exit.");
        return profileModel;
    }

    @Override
    @CacheEvict(value = {"business_profile"}, keyGenerator = "businessProfileCacheKeyGeneratorById")
    public String deleteBusinessProfile(String id) {
        log.info(">> [deleteBusinessProfile] called.");
        businessProfileRepository.deleteById(id);
        log.info("<< [deleteBusinessProfile] Exit.");
        return id;
    }

    @Override
    @Cacheable(value = {"business_profile"}, keyGenerator = "businessProfileCacheKeyGeneratorById")
    public BusinessProfileModel getBusinessProfileById(String id) throws BusinessException {
        log.info(">> [getBusinessProfileById] called.");
        BusinessProfileModel profileModel = null;
        try {
            profileModel = businessProfileRepository.findById(id).orElse(null);
            if (profileModel == null) {
                throw new InvalidInputException("Business Profile Not found for Id: " + id);
            }
        } catch (Exception ex) {
            log.error("Failed to Fetch Business Profile Not found for Id {}", id);
            Error error = new Error(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), ex.getMessage());
            throw new BusinessException(HttpStatus.valueOf(error.getCode()).value(), error, ex.getMessage(), ex);
        }
        log.info("<< [getBusinessProfileById] Exit.");
        return profileModel;
    }

    @Override
    public Boolean validateBusinessProfile(BusinessProfileModel model) throws BusinessException {

        final String validateBusinessProfileEndPointUrl = businessProfileProperty.getValidationServiceEndPoints();
        String[] validationUrls = validateBusinessProfileEndPointUrl.split(",");
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();

        for (String validationUrl : validationUrls) {
            try {
                URI uri = new URI(validationUrl);
                futures.add(CompletableFuture.supplyAsync(() -> validatorIntegrationService.validateBusinessProfileUpdate(uri), threadPoolExecutor));
            } catch (Exception ex) {
                Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.name(), "Business Profile Validation Failed", ex.getMessage());
                throw new BusinessException(HttpStatus.valueOf(error.getCode()).value(), error, ex.getMessage(), ex);
            }
        }

        for (CompletableFuture<Boolean> completableFuture : futures) {
            if (!completableFuture.join()) {
                return false;
            }
        }
        return true;
    }

}
