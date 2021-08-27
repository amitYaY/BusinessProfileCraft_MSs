package com.craft.service;

import com.craft.dao.BusinessProfileRepository;
import com.craft.service.impl.BusinessProfileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class BusinessProfileServiceTest {

    @InjectMocks
    private BusinessProfileServiceImpl businessProfileService;

    @Mock
    private BusinessProfileRepository businessProfileRepository;

    @Test
    public void testDeleteBusinessProfile() {
        String id = "dummy@email.com";
        doNothing().when(businessProfileRepository).deleteById(id);
        String res = businessProfileService.deleteBusinessProfile(id);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(id, res);
    }

}
