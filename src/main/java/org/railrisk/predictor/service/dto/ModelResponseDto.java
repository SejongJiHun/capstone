package org.railrisk.predictor.service.dto;

import lombok.Data;

@Data
public class ModelResponseDto {
    private String group;
    private String cause;
    private String detail;
    private RegressionResult regression;

    @Data
    public static class RegressionResult {
        private double log_total_damage;
        private double total_damage;
        private double deaths;
        private double severe_injuries;
        private double minor_injuries;
    }
}
