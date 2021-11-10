package com.kms.seft203.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor @AllArgsConstructor
public class WidgetDto {
    private Integer dashboard_id;
    private String type;
    private Integer minWidth;
    private Integer minHeight;
    private Map<String, String> configs;
}
