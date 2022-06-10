package com.craft.dao;

import com.craft.model.TestCases;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends CrudRepository<TestCases, Integer> {

    public List<TestCases> fetchAllByQuestion(String questionId);
}
