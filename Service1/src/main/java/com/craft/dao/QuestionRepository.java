package com.craft.dao;

import com.craft.model.Questions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Questions, Integer> {

    Questions findByQuestionId(String qId);
}
