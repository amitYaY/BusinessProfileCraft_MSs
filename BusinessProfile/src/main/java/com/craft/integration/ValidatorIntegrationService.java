package com.craft.integration;

import com.craft.constant.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Component
public class ValidatorIntegrationService {

    private final RestTemplate restTemplate;

    @Autowired
    public ValidatorIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = Constants.VALIDATOR_CB, fallbackMethod = "validationFallback")
    public Boolean validateBusinessProfileUpdate(URI uri) {
        ResponseEntity<Boolean> result = restTemplate.getForEntity(uri, Boolean.class);
        log.info("[validateBusinessProfileUpdate] result: " + result.getStatusCode() + " Body: " + result.getBody());
        return result.getBody();
    }

    public Boolean validationFallback(Throwable ex) {
        log.info("fallback method is called. Circuit is Open for ValidatorService");
        log.error("Circuit is Open for ValidatorService. Exception: ", ex);
        return false;
    }

    /*public Boolean validateBusinessProfileV2(BusinessProfileModel model) throws ExecutionException, InterruptedException {

        Future<Boolean> res1 = threadPoolExecutor.submit(this::validateBusinessProfileUpdate_1);
        Future<Boolean> res2 = threadPoolExecutor.submit(this::validateBusinessProfileUpdate_2);
        Future<Boolean> res3 = threadPoolExecutor.submit(this::validateBusinessProfileUpdate_3);

        if(res1.get() && res2.get() && res3.get()) {
            return true;
        }
        return false;
    }

    @CircuitBreaker(name = Constants.VALIDATOR_CB, fallbackMethod = "validationFallback")
    public Boolean validateBusinessProfileUpdate_1() throws URISyntaxException {
        final String validateBusinessProfileEndPointUrl = businessProfileProperty.getValidationServiceOneBaseUrl() + "craftdemo/validate";
        URI uri = new URI(validateBusinessProfileEndPointUrl);
        ResponseEntity<Boolean> result = restTemplate.getForEntity(uri, Boolean.class);
        log.info("[validateBusinessProfileUpdate] result: " + result.getStatusCode() + " Body: " + result.getBody());
        return result.getBody();
    }

    @CircuitBreaker(name = Constants.VALIDATOR_CB, fallbackMethod = "validationFallback")
    public Boolean validateBusinessProfileUpdate_2() throws URISyntaxException {
        final String validateBusinessProfileEndPointUrl = businessProfileProperty.getValidationServiceTwoBaseUrl() + "craftdemo/validate";
        URI uri = new URI(validateBusinessProfileEndPointUrl);
        ResponseEntity<Boolean> result = restTemplate.getForEntity(uri, Boolean.class);
        log.info("[validateBusinessProfileUpdate] result: " + result.getStatusCode() + " Body: " + result.getBody());
        return result.getBody();
    }

    @CircuitBreaker(name = Constants.VALIDATOR_CB, fallbackMethod = "validationFallback")
    public Boolean validateBusinessProfileUpdate_3() throws URISyntaxException {
        final String validateBusinessProfileEndPointUrl = businessProfileProperty.getValidationServiceThreeBaseUrl() + "craftdemo/validate";
        URI uri = new URI(validateBusinessProfileEndPointUrl);
        ResponseEntity<Boolean> result = restTemplate.getForEntity(uri, Boolean.class);
        log.info("[validateBusinessProfileUpdate] result: " + result.getStatusCode() + " Body: " + result.getBody());
        return result.getBody();
    }*/

}
