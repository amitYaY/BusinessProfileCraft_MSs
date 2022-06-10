package com.craft.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "QUESTION")
public class Questions {

    @Id
    private Integer id;

    @Column(name = "QUESTION_ID")
    private String questionId;

    @OneToMany(mappedBy = "question")
    private List<TestCases> testCases;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "QUES_DESCRIPTION")
    private String quesDesc;

    @Column(name = "MARKS")
    private Integer marks;
}
