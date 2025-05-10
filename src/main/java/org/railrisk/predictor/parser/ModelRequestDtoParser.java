package org.railrisk.predictor.parser;

import org.railrisk.predictor.domain.Station;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.parser.dto.StationWeatherDto;
import org.railrisk.predictor.service.dto.ModelRequestDto;


public class ModelRequestDtoParser {
    public static ModelRequestDto modelRequestDtoParser(Weather weather, Station station, String stationLine) {
        ModelRequestDto dto = new ModelRequestDto();

        // Weather 객체로부터 값 추출 및 매핑
        dto.setDate(weather.getTime()); // 일자
        dto.setTemp(parseDoubleOrNull(weather.getTa())); // 온도
        dto.setRain(parseDoubleOrNull(weather.getRn_day())); // 비
        dto.setWind(parseDoubleOrNull(weather.getWs())); // 풍속
        dto.setHumidity(parseDoubleOrNull(weather.getHm())); // 습도
        dto.setPressure(parseDoubleOrNull(weather.getPa())); // 기압
        dto.setSnow(parseDoubleOrNull(weather.getSd_day())); // 눈
        dto.setFog(weather.getFog()); // 안개

        // Station 객체로부터 값 추출 및 매핑
        dto.setRegion(station.getStnName()); // stnName
        dto.setLine(stationLine);  // 노선은 파라미터로 직접 넣어야함
        dto.setStation(station.getStationName() + "역"); // "역" 추가해서  파싱
        dto.setPassenger(station.getAvg() != null ? Math.round(station.getAvg()) : 0); // 소수점 평균 반올림

        return dto;
    }

    public static ModelRequestDto modelRequestDtoParser(StationWeatherDto stationWeatherDto) {
        Weather weather = stationWeatherDto.getWeather();
        Station station = stationWeatherDto.getStation();

        ModelRequestDto dto = new ModelRequestDto();

        // Weather 객체로부터 값 추출 및 매핑
        dto.setDate(weather.getTime()); // 일자
        dto.setTemp(parseDoubleOrNull(weather.getTa())); // 온도
        dto.setRain(parseDoubleOrNull(weather.getRn_day())); // 비
        dto.setWind(parseDoubleOrNull(weather.getWs())); // 풍속
        dto.setHumidity(parseDoubleOrNull(weather.getHm())); // 습도
        dto.setPressure(parseDoubleOrNull(weather.getPa())); // 기압
        dto.setSnow(parseDoubleOrNull(weather.getSd_day())); // 눈
        dto.setFog(weather.getFog()); // 안개

        // Station 객체로부터 값 추출 및 매핑
        dto.setRegion(station.getStnName()); // stnName
        //dto.setLine(stationLine);  // 노선필드를 추가해야함
        dto.setStation(station.getStationName() + "역"); // "역" 추가해서  파싱
        dto.setPassenger(station.getAvg() != null ? Math.round(station.getAvg()) : 0); // 소수점 평균 반올림

        return dto;


    }

    private static Double parseDoubleOrNull(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

}
