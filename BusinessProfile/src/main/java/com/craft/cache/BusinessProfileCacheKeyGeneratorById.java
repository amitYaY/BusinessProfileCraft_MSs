package com.craft.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class BusinessProfileCacheKeyGeneratorById implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        String profileId = (String) params[0];
        return profileId;
    }

}
