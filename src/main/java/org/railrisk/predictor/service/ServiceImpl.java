package org.railrisk.predictor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.railrisk.predictor.api.OriginWeatherApi;
import org.railrisk.predictor.domain.Station;
import org.railrisk.predictor.domain.StationPredict;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.parser.WeatherParser;
import org.railrisk.predictor.repository.StationRepositoryInter;
import org.railrisk.predictor.repository.StationPredictRepositoryInter;
import org.railrisk.predictor.repository.WeatherRepositoryInter;
import org.railrisk.predictor.service.dto.ModelRequestDto;
import org.railrisk.predictor.service.dto.ModelResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ServiceImpl implements ServiceInter{
    private final WeatherRepositoryInter weatherRepositoryInter;
    private final StationRepositoryInter stationRepositoryInter;
    private final OriginWeatherApi originWeatherApi;
    private final StationPredictRepositoryInter stationPredictRepositoryInter;

    @Override
    public List<Weather> fetchAndSaveWeather() {
        log.info("🌦️ 모든 지점 날씨 데이터 수집 시작");
        String rawText = originWeatherApi.getRawWeatherText(); // api로 날씨데이터 가져오기
        List<Weather> weatherList = WeatherParser.parse(rawText); // 파싱 후 리스트에 저장

        System.out.println(weatherList);
        // 날씨데이터가 있으면 DB에 저장
        if (weatherList.isEmpty()) {
            log.warn("⚠️ 저장할 날씨 데이터가 없습니다.");
        } else {
            weatherRepositoryInter.deleteAll();
            weatherRepositoryInter.saveAll(weatherList);
            log.info("✅ 날씨 데이터 {}건 저장 완료", weatherList.size());
            log.info("🌦️ 모든 지점 날씨 데이터 수집 완료");
        }
        return weatherList;

    }

    @Override
    public Weather findWeatherByName(String stationName) {
        log.info("🔍 '{}' 역의 날씨 데이터 조회 시작", stationName);

        Station station = stationRepositoryInter.findByStationName(stationName)
                .orElseThrow(() -> new IllegalArgumentException("해당 역이 존재하지 않습니다: " + stationName));

        String stn_num = station.getStnNum();

        // 2. stn으로 origin_weather 데이터 조회
        Weather weather = weatherRepositoryInter.findByStn(stn_num)
                .orElseThrow(() -> new IllegalArgumentException("해당 역의 날씨 데이터가 존재하지 않습니다: " + stn_num));

        log.info("🔍 '{}' 역의 날씨 데이터 조회 완료", stationName);
        return weather;
    }


    private final RestTemplate restTemplate = new RestTemplate();
    //private final String flaskUrl = "http://localhost:5000/predict"; // Flask 서버 주소

    @Override
    public List<ModelResponseDto> sendDataToModel(List<ModelRequestDto> requestList){
        log.info("javaSpring -> flask");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<ModelRequestDto>> entity = new HttpEntity<>(requestList, headers);

        ResponseEntity<ModelResponseDto[]> response = restTemplate.exchange(
                "http://localhost:5000/predict_batch",
                HttpMethod.POST,
                entity,
                ModelResponseDto[].class
        );

        log.info("flask -> javaSpring");
        return Arrays.asList(response.getBody());
    }

    @Override
    public Station findStationByStationName(String stationName) {
        Station station = stationRepositoryInter.findByStationName(stationName)
                .orElseThrow(() -> new IllegalArgumentException("해당 역이 존재하지 않습니다: " + stationName));
        return station;
    }

    @Override
    public List<StationPredict> findAllStationPredicts(){
        return stationPredictRepositoryInter.findAll();
    }

}
