package org.railrisk.predictor.service;

import org.railrisk.predictor.domain.Station;
import org.railrisk.predictor.domain.StationPredict;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.service.dto.ModelRequestDto;
import org.railrisk.predictor.service.dto.ModelResponseDto;

import java.util.List;

public interface ServiceInter {

    // 날씨 API로 모든 지점에 대한 날씨데이터 가져오기
    List<Weather> fetchAndSaveWeather();

    // 특정 역의 날씨 데이터 가져오기
    Weather findWeatherByName(String stationName);

    // 특정 지점의 날씨 데이터 가져오기
    Weather findWeatherByStn(String stn);

    // 모델과 통신
    List<ModelResponseDto> sendDataToModel(List<ModelRequestDto> requestList);

    // 역명으로 Station 객체 가져오기
    Station findStationByStationName(String stationName);

    // 모든 역에 대한 위험도 가져오기
    List<StationPredict> findAllStationPredicts();
}
