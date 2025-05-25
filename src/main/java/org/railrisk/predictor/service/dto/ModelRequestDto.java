package org.railrisk.predictor.service.dto;

import lombok.Data;

@Data
public class ModelRequestDto {
    private String dayOfWeek;        // 요일
    private String railType;         // 철도구분 (예: 도시철도)
    private String line;             // 노선 (예: 2호선)
    private String stationName;      // 발생장소 (예: 서울역)
    private String stn;              // 지역명_업데이트 (예: 서울특별시)

    private Double ta;          // 평균기온(°C)
    private Double rn_day;      // 일강수량(mm)
    private Double ws;          // 평균풍속(m/s)
    private Double hm;          // 평균 상대습도(%)
    private Double ts;          // 평균 지면온도(°C)


    public ModelRequestDto() {
    }

    public ModelRequestDto(String dayOfWeek, String railType, String line, String stationName, String stn, Double ta, Double rn_day, Double ws, Double hm, Double ts) {
        this.dayOfWeek = dayOfWeek;
        this.railType = railType;
        this.line = line;
        this.stationName = stationName;
        this.stn = stn;
        this.ta = ta;
        this.rn_day = rn_day;
        this.ws = ws;
        this.hm = hm;
        this.ts = ts;
    }
}
