package com.jszor.cdq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateTaskRequest {
    private String input;
    private String pattern;
}
