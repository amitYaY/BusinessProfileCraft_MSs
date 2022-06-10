package com.craft.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestEvalRequestDTO {

    private String code;

    private List<String> tests;

}
