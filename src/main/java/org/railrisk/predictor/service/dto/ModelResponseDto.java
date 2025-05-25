package org.railrisk.predictor.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModelResponseDto {


    @JsonProperty("predicted_cause")       // 예측된 사고 원인 (예: "환경요인", "선로및구조물" 등)
    private String predictedCause;

    @JsonProperty("damage_risk")           // 피해액 기반 위험도 (1~5등급, 피해액 크기에 따라 결정됨)
    private int damageRisk;

    @JsonProperty("class_risk")            // 사고 유형 자체의 고정 위험도 점수 (사전 정의된 위험도 가중치)
    private int classRisk;

    @JsonProperty("combined_risk")         // 복합 위험도 점수 (0.6 * 피해위험도 + 0.4 * 유형위험도)
    private double combinedRisk;

    public ModelResponseDto() {
    }

    public ModelResponseDto(String predictedCause, int damageRisk, int classRisk, double combinedRisk) {
        this.predictedCause = predictedCause;
        this.damageRisk = damageRisk;
        this.classRisk = classRisk;
        this.combinedRisk = combinedRisk;
    }
}
