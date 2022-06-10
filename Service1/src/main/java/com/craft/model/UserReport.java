package com.craft.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "USER_REPORT")
public class UserReport {

    @Id
    private Integer id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "COMPETITION_ID")
    private String competitionId;

    @Column(name = "QUESTION_ID")
    private String questionId;

    @Column(name = "MARKS")
    private Integer marks;
}
