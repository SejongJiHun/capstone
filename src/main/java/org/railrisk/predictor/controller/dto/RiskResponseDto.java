package org.railrisk.predictor.controller.dto;

import lombok.Data;

@Data
public class RiskResponseDto {


    private String stationName; // 역명
    private String line; // 노선
    private double riskScore;

    public RiskResponseDto() {
    }

    public RiskResponseDto(String stationName, String line, double riskScore) {
        this.stationName = stationName;
        this.line = line;
        this.riskScore = riskScore;
    }
}
