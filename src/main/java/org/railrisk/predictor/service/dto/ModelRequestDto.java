package org.railrisk.predictor.service.dto;

import lombok.Data;

@Data
public class ModelRequestDto {
    private String date;                 // 일자 (예: "2023-12-15")
    private String region;               // 지역명 (예: "서울")
    private String line;                 // 노선 (예: "1호선")
    private String station;              // 발생장소 (예: "구로역")
    private Double temp;                 // 평균기온(°C)
    private Double rain;                 // 강수량
    private Double wind;                 // 평균풍속(m/s)
    private Double humidity;             // 평균 상대습도(%)
    private Double pressure;             // 평균기압(hPa)
    private Double snow;                 //적설량
    private String fog;                  // 안개유무 ("안개_유" / "안개_무")
    private Integer passenger;           // 일평균 이용객


    public ModelRequestDto() {
    }

    public ModelRequestDto(String date, String region, String line, String station, Double temp, Double rain, Double wind, Double humidity, Double pressure, Double snow, String fog, Integer passenger) {
        this.date = date;
        this.region = region;
        this.line = line;
        this.station = station;
        this.temp = temp;
        this.rain = rain;
        this.wind = wind;
        this.humidity = humidity;
        this.pressure = pressure;
        this.snow = snow;
        this.fog = fog;
        this.passenger = passenger;
    }
}
