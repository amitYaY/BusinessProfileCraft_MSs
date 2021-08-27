package com.craft.cache;

import com.craft.dto.BusinessProfileDTO;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class BusinessProfileCacheKeyGeneratorForCreate implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        BusinessProfileDTO businessProfileDTO = (BusinessProfileDTO) params[0];
        return businessProfileDTO.getId();
    }

}
