package com.craft.dao;

import com.craft.model.UserReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportRepository extends CrudRepository<UserReport, Integer> {

    UserReport fetchByUserIdAndCompetitionIdAndQuestionId(String userId, String compId, String qId);
}
