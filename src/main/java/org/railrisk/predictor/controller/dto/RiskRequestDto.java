package org.railrisk.predictor.controller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RiskRequestDto {

    @JsonProperty("역명")
    private String stationName; // 역명
    @JsonProperty("노선")
    private String line; // 노선
    @JsonProperty("지점번호")
    private String stnNumber; // 기상청 관측 지점번호

    public RiskRequestDto() {
    }

    public RiskRequestDto(String stationName, String line, String stnNumber) {
        this.stationName = stationName;
        this.line = line;
        this.stnNumber = stnNumber;
    }
}
