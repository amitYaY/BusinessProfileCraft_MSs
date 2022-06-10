package com.craft.manager;

import com.craft.dao.TestCaseRepository;
import com.craft.dao.UserReportRepository;
import com.craft.dto.TestEvalRequestDTO;
import com.craft.helper.ServiceManagerHelper;
import com.craft.model.TestCases;
import com.craft.model.UserReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class ServiceManager {

    private final ServiceManagerHelper serviceManagerHelper;

    private final RestTemplate restTemplate;

    private final TestCaseRepository testCaseRepository;

    private final UserReportRepository userReportRepository;

    private static final String EVALUATION_HOST = "http://localhost:8081/evaluate";

    public UserReport evaluateCode(String code, String questionId, String userId, String compId) {
        List<TestCases> tests = testCaseRepository.fetchAllByQuestion(questionId);
        TestEvalRequestDTO requestDTO = serviceManagerHelper.createTestEvalRequestDTO(code, tests);
        try {
            Integer marks = serviceManagerHelper.findMarksForQuestion(questionId);
            if(marks == null) {
                return null;
            }
            if (callToEvaluationService(questionId, requestDTO)) {
                UserReport userReport = userReportRepository.fetchByUserIdAndCompetitionIdAndQuestionId(userId, compId, questionId);
                if (Objects.nonNull(userReport)) {
                    userReport.setMarks(marks);
                } else {
                    userReport = new UserReport();
                    userReport.setUserId(userId);
                    userReport.setCompetitionId(compId);
                    userReport.setQuestionId(questionId);
                    userReport.setMarks(marks);
                }
                return userReportRepository.save(userReport);
            } else {
                UserReport userReport = userReportRepository.fetchByUserIdAndCompetitionIdAndQuestionId(userId, compId, questionId);
                if (!Objects.nonNull(userReport)) {
                    userReport = new UserReport();
                    userReport.setUserId(userId);
                    userReport.setCompetitionId(compId);
                    userReport.setQuestionId(questionId);
                    userReport.setMarks(0);
                }
                return userReportRepository.save(userReport);
            }
        } catch (Exception ex) {
            log.error("Error while Code Evaluation");
            return null;
        }
    }

    public Boolean callToEvaluationService(String questionId, TestEvalRequestDTO requestDTO) {
        log.info("[callToEvaluationService] Called for questionId: "+questionId);
        Boolean response = false;
        try {
            URI uri = new URI(EVALUATION_HOST+"?questionId="+questionId);
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<TestEvalRequestDTO> httpEntity = new HttpEntity<>(requestDTO, httpHeaders);
            response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Boolean.class).getBody();
        } catch (URISyntaxException ex) {
            log.error("[callToEvaluationService] error while formmhg URI");
        }
        return response;
    }
}
