package com.craft.controller;

import com.craft.common.Response;
import com.craft.controller.impl.BusinessProfileControllerImpl;
import com.craft.service.impl.BusinessProfileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusinessProfileControllerTest {

    @InjectMocks
    private BusinessProfileControllerImpl businessProfileController;

    @Mock
    private BusinessProfileServiceImpl businessProfileService;

    @Test
    public void testDeleteBusinessProfile() {
        String id = "dummy@email.com";
        when(businessProfileService.deleteBusinessProfile(id)).thenReturn(id);
        Response<String> res = businessProfileController.deleteBusinessProfile(id);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(id, res.getPayload());
    }

}
