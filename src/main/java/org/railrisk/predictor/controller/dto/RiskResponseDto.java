package org.railrisk.predictor.controller.dto;

import lombok.Data;
import org.railrisk.predictor.service.dto.ModelResponseDto;

@Data
public class RiskResponseDto {


    private String stationName; // 역명
    private String line; // 노선
    private double riskScore;
    private ModelResponseDto modelResponseDto;

    public RiskResponseDto() {
    }

    public RiskResponseDto(String stationName, String line, double riskScore, ModelResponseDto modelResponseDto) {
        this.stationName = stationName;
        this.line = line;
        this.riskScore = riskScore;
        this.modelResponseDto = modelResponseDto;
    }
}
