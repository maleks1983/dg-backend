package com.app.dgBackend.dto;

import lombok.Data;

@Data
public class UpdateProductRequest {
    private Integer oldSerial;
    private Integer newSerial;

}
