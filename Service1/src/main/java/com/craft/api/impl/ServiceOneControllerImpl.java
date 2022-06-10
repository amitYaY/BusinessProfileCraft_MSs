package com.craft.api.impl;

import com.craft.api.ServiceOneController;
import com.craft.manager.ServiceManager;
import com.craft.model.UserReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServiceOneControllerImpl implements ServiceOneController {

    private final ServiceManager serviceManager;

    @Override
    public ResponseEntity<UserReport> submitCode(String code, String questionId, String userId, String compId) {

        if(code == null || code.trim().length() == 0 || questionId == null || questionId.trim().length() == 0
                || userId == null || userId.trim().length() == 0 || compId == null || compId.trim().length() == 0 ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserReport userReport = serviceManager.evaluateCode(code, questionId, userId, compId);
        if(Objects.nonNull(userReport)) {
            return ResponseEntity.status(HttpStatus.OK).body(userReport);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
