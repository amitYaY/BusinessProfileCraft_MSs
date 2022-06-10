package com.craft.helper;

import com.craft.dao.QuestionRepository;
import com.craft.dto.TestEvalRequestDTO;
import com.craft.model.Questions;
import com.craft.model.TestCases;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ServiceManagerHelper {

    private final QuestionRepository questionRepository;

    public TestEvalRequestDTO createTestEvalRequestDTO(String code, List<TestCases> tests) {
        TestEvalRequestDTO requestDTO = new TestEvalRequestDTO();
        requestDTO.setCode(code);
        requestDTO.setTests(tests.stream().map(TestCases::getInput).collect(Collectors.toList()));
        return requestDTO;
    }

    public Integer findMarksForQuestion(String questionId) {
        Questions questions = questionRepository.findByQuestionId(questionId);
        if(Objects.nonNull(questions)) {
            return questions.getMarks();
        }
        return null;
    }

}
