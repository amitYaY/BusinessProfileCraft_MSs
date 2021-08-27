package com.craftdemo.controller.impl;

import com.craftdemo.controller.ValidationService;
import org.springframework.stereotype.Component;

@Component
public class ValidationServiceImpl implements ValidationService {

    @Override
    public Boolean validateBusinessProfile() throws InterruptedException {
        Thread.sleep(1000);
        return true;
    }

}
