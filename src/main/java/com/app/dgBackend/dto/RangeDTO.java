package com.app.dgBackend.dto;

import lombok.Data;

@Data
public class RangeDTO {
    private Integer start;
    private Integer end;

    public RangeDTO(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }
}
