package org.railrisk.predictor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.railrisk.predictor.controller.dto.RiskRequestDto;
import org.railrisk.predictor.controller.dto.RiskResponseDto;
import org.railrisk.predictor.domain.Station;
import org.railrisk.predictor.domain.StationPredict;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.parser.ModelRequestDtoParser;
import org.railrisk.predictor.service.ServiceInter;
import org.railrisk.predictor.service.dto.ModelRequestDto;
import org.railrisk.predictor.service.dto.ModelResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequiredArgsConstructor
public class JsController {
    private final ServiceInter serviceInter;

    @ResponseBody
    @GetMapping("/api/station-weathers") // 역에 대한 날씨데이터 가져오기
    public ResponseEntity<Map<String, Object>> getStationDataWeather(@RequestParam("station_name") String stationName) {

        serviceInter.fetchAndSaveWeather();
        Weather weather = serviceInter.findWeatherByName(stationName); // 해당 역의 날씨데이터 가져오기
        Station station = serviceInter.findStationByStationName(stationName);


        Map<String, Object> response = new HashMap<>();
        response.put("weather", weather); // 날씨 데이터 반환
        response.put("station", station.getStnName());
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @GetMapping("/api/station-predicts") // 검색창으로 역의 위험도 예측 값 가져오기
    public ResponseEntity<List<ModelResponseDto>> getStationPredict(@RequestParam("station_name") String stationName,
                                                                    @RequestParam("rail_type") String railType,
                                                                    @RequestParam("station_line") String stationLine) {
        List<ModelRequestDto> requestList = new ArrayList<>();

        log.info("{}역의 Weather, Station 객체 가져오기", stationName);
        Weather weather = serviceInter.findWeatherByName(stationName); // 해당 역의 weather 가져오기
        Station station = serviceInter.findStationByStationName(stationName); // 해당 역의 station 정보 가져오기
        log.info("{}역의 Weather, Station 객체 가져오기 완료", stationName);

        // 파싱을 통해 모델 입력에 필요한 것만 선택 후 저장
        log.info("파싱중");
        ModelRequestDto modelRequestDto =
                ModelRequestDtoParser.modelRequestDtoParser(weather, station, stationLine, railType);
        log.info("파싱완료");

        // 모델에 전달할 리스트에 추가
        requestList.add(modelRequestDto);

        log.info("모델에 입력값 전달");
        // 모델에 리스트 넘기기
        List<ModelResponseDto> response = serviceInter.sendDataToModel(requestList);
        log.info("모델 결과 전달 받음");

        log.info("결과{}", response);

        return ResponseEntity.ok(response);
    }


    @ResponseBody
    @PostMapping("/api/riskScore") // 위험도 갱신 버튼
    public List<RiskResponseDto> getRiskScore(@RequestBody List<RiskRequestDto> stations) {
        serviceInter.fetchAndSaveWeather(); // 날씨 갱신

        List<ModelRequestDto> requestList = new ArrayList<>(); // 모델에 전달할 리스트


        // 1. 입력된 역 리스트를 기반으로 ModelRequestDto 리스트 생성
        log.info("입력된 역 리스트를 기반으로 ModelRequestDto 리스트 생성 시작");
        for (RiskRequestDto s : stations) {
            String stationName = s.getStationName();
            String line = s.getLine();
            String stnNumber = s.getStnNumber();
            String railType = "도시철도";

            Weather weather = serviceInter.findWeatherByStn(stnNumber);
            Station station = serviceInter.findStationByStationName(stationName);

            ModelRequestDto modelRequestDto = ModelRequestDtoParser.modelRequestDtoParser(weather, station, line, railType);
            requestList.add(modelRequestDto);
        }
        log.info("입력된 역 리스트를 기반으로 ModelRequestDto 리스트 생성 완료");



        // 2. 모델에 입력값 전달 → 예측 결과 리스트 받기
        List<ModelResponseDto> modelOutputs = serviceInter.sendDataToModel(requestList);

        // 3. 프론트에 전달할 결과 리스트 생성
        List<RiskResponseDto> resultList = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < stations.size(); i++) {
            RiskRequestDto input = stations.get(i);
            ModelResponseDto modelOutput = modelOutputs.get(i);

            double riskScore = Math.round(random.nextDouble() * 1000) / 10.0;

            RiskResponseDto riskResponseDto = new RiskResponseDto(
                    input.getStationName(),
                    input.getLine(),
                    riskScore,
                    modelOutput
            );
            resultList.add(riskResponseDto);
        }

        return resultList;
    }


}
