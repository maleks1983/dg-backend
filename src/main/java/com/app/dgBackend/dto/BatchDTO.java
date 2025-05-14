package com.app.dgBackend.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BatchDTO {

    private Integer id;

    private Set<RangeDTO> ranges;

    private Integer quantity;

    private String firmware;

    public BatchDTO(Integer id, Set<RangeDTO> ranges, int size, String firmware) {
        this.id = id;
        this.ranges = ranges;
        this.quantity = size;
        this.firmware = firmware;
    }
}