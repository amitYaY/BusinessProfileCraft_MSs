package com.craft.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
public class BusinessProfileProperty {

    @Value("${corePoolSize}")
    private int corePoolSize;

    @Value("${maxPoolSize}")
    private int maxPoolSize;

    @Value("${queueCapacity}")
    private int queueCapacity;

    @Value("${validationServiceEndPoints}")
    private String validationServiceEndPoints;

    /*@Value("${validationServiceOneBaseUrl}")
    private String validationServiceOneBaseUrl;

    @Value("${validationServiceTwoBaseUrl}")
    private String validationServiceTwoBaseUrl;

    @Value("${validationServiceThreeBaseUrl}")
    private String validationServiceThreeBaseUrl;*/

}
