package com.craft.kafka.producer;

import com.craft.domain.BusinessProfileModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class BusinessProfileKafkaProducer {

    private KafkaTemplate<String, BusinessProfileModel> kafkaTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public BusinessProfileKafkaProducer(KafkaTemplate<String, BusinessProfileModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishBusinessProfileForValidation(BusinessProfileModel businessProfileModel) {

        ListenableFuture<SendResult<String, BusinessProfileModel>>  listenableFuture = kafkaTemplate.send("businessprofile", businessProfileModel.getId(), businessProfileModel);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, BusinessProfileModel>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Failed to Publish BusinessProfileValidation to Topic:businessprofile.", ex);
            }

            @Override
            public void onSuccess(SendResult<String, BusinessProfileModel> result) {
                log.info("Publish BusinessProfileValidation to Topic:businessprofile Success for Key: "+result.getProducerRecord().key());
            }
        });
    }

}
