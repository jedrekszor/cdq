package com.jszor.cdq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetTaskDataResponse {
    private Long position;
    private Long typos;
    private Long status;
}
