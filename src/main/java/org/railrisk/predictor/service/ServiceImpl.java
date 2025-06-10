package org.railrisk.predictor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.railrisk.predictor.api.OriginWeatherApi;
import org.railrisk.predictor.domain.Station;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.parser.WeatherParser;
import org.railrisk.predictor.repository.StationRepositoryInter;
import org.railrisk.predictor.repository.WeatherRepositoryInter;
import org.railrisk.predictor.service.dto.ModelRequestDto;
import org.railrisk.predictor.service.dto.ModelResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ServiceImpl implements ServiceInter{
    private final WeatherRepositoryInter weatherRepositoryInter;
    private final StationRepositoryInter stationRepositoryInter;
    private final OriginWeatherApi originWeatherApi;


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

    @Override // 역명으로 날씨 데이터 조회
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

    @Override // 지점번호로 날씨 데이터 조회
    public Weather findWeatherByStn(String stn) {

        Weather weather = weatherRepositoryInter.findByStn(stn)
                .orElseThrow(() -> new IllegalArgumentException("해당 역의 날씨 데이터가 존재하지 않습니다: " + stn));

        return weather;
    }


    private final RestTemplate restTemplate = new RestTemplate();

    @Override // 모델과 통신
    public List<ModelResponseDto> sendDataToModel(List<ModelRequestDto> requestList){
        log.info("javaSpring -> flask");

        List<Map<String, Object>> payload = new ArrayList<>();

        for (ModelRequestDto dto : requestList) {
            Map<String, Object> mapped = new LinkedHashMap<>();
            mapped.put("요일", dto.getDayOfWeek());
            mapped.put("철도구분", dto.getRailType());
            mapped.put("노선", dto.getLine());
            mapped.put("발생장소", dto.getStationName());
            mapped.put("지역명_업데이트", dto.getStn());
            mapped.put("평균기온(°C)", dto.getTa());
            mapped.put("일강수량(mm)", dto.getRn_day());
            mapped.put("평균풍속(m/s)", dto.getWs());
            mapped.put("평균 상대습도(%)", dto.getHm());
            mapped.put("평균 지면온도(°C)", dto.getTs());

            payload.add(mapped);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Map<String, Object>>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<ModelResponseDto[]> response = restTemplate.postForEntity(
                "http://localhost:5000/predict_batch_v3",
                entity,
                ModelResponseDto[].class
        );

        log.info("flask -> javaSpring");

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override // 역명으로 역 데이터 조회
    public Station findStationByStationName(String stationName) {
        Station station = stationRepositoryInter.findByStationName(stationName)
                .orElseThrow(() -> new IllegalArgumentException("해당 역이 존재하지 않습니다: " + stationName));
        return station;
    }



}
