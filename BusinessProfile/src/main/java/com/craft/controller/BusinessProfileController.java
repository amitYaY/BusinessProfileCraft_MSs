package com.craft.controller;

import com.craft.common.Response;
import com.craft.domain.BusinessProfileModel;
import com.craft.dto.BusinessProfileDTO;
import com.craft.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "BusinessProfileController")
@RestController
@RequestMapping(path = "/craft")
public interface BusinessProfileController {

    @ApiOperation(value = "Create Business Profile", httpMethod = "POST", consumes = "application/json", produces = "application/json")
    @RequestMapping(path = "/business/profile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Response<BusinessProfileModel> createBusinessProfile(@RequestBody @Valid BusinessProfileDTO businessProfile) throws BusinessException;

    @ApiOperation(value = "Update Business Profile", httpMethod = "PUT", consumes = "application/json", produces = "application/json")
    @RequestMapping(path = "/business/profile/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Response<BusinessProfileModel> updateBusinessProfile(@RequestBody BusinessProfileDTO businessProfile, @PathVariable(name = "id", required = true) String id) throws BusinessException;

    @ApiOperation(value = "Delete Business Profile", httpMethod = "DELETE", produces = "application/json")
    @RequestMapping(path = "/business/profile/{id}", method = RequestMethod.DELETE)
    Response<String> deleteBusinessProfile(@PathVariable(name = "id", required = true) String id);

    @ApiOperation(value = "Fetch Active Business Profile By Id", httpMethod = "GET", produces = "application/json")
    @RequestMapping(path = "/business/profile/active", method = RequestMethod.GET)
    Response<BusinessProfileModel> getActiveBusinessProfileById(@RequestParam(name = "id", required = true) String id) throws BusinessException;

    @ApiOperation(value = "Fetch Business Profile By Id", httpMethod = "GET", produces = "application/json")
    @RequestMapping(path = "/business/profile", method = RequestMethod.GET)
    Response<BusinessProfileModel> getBusinessProfileById(@RequestParam(name = "id", required = true) String id) throws BusinessException;

    @ApiOperation(value = "Get Business Profile Status For Id", httpMethod = "GET", produces = "application/json")
    @RequestMapping(path = "/business/profile/status", method = RequestMethod.GET)
    Response<String> getBusinessProfileStatus(@RequestParam(name = "id", required = true) String id) throws BusinessException;

}