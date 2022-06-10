package com.craft.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TEST_CASE")
public class TestCases {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Questions question;

    @Column(name = "TEST_CASE")
    private String input;


}
