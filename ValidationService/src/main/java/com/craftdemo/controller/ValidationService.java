package com.craftdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/craftdemo")
public interface ValidationService {

    @RequestMapping(path = "/validate", method = RequestMethod.GET)
    Boolean validateBusinessProfile() throws InterruptedException;

}
