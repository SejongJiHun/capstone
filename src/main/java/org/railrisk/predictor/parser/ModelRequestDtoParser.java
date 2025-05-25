package org.railrisk.predictor.parser;

import org.railrisk.predictor.domain.Station;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.service.dto.ModelRequestDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;


public class ModelRequestDtoParser {
    public static ModelRequestDto modelRequestDtoParser(Weather weather, Station station, String stationLine, String railType) {
        ModelRequestDto dto = new ModelRequestDto();

        // Weather 객체로부터 값 추출 및 매핑
        dto.setTa(Double.parseDouble(weather.getTa()));
        dto.setRn_day(Double.parseDouble(weather.getRn_day()));
        dto.setWs(Double.parseDouble(weather.getWs()));
        dto.setHm(Double.parseDouble(weather.getHm()));
        dto.setTs(Double.parseDouble(weather.getTs()));

        // Station 객체로부터 값 추출 및 매핑
        dto.setStn(station.getStnName()); // stnName
        dto.setStationName(station.getStationName());

        // 노선, 철도구분 매핑
        dto.setLine(stationLine);
        dto.setRailType(railType);

        // 요일 매핑
        dto.setDayOfWeek(getKoreanDayOfWeek());




        return dto;
    }

    private static String getKoreanDayOfWeek() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN); // "월", "화", ...
    }

    private static Double parseDoubleOrNull(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

}
