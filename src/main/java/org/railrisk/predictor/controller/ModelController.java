//package org.railrisk.predictor.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.railrisk.predictor.domain.Station;
//import org.railrisk.predictor.domain.Weather;
//import org.railrisk.predictor.parser.ModelRequestDtoParser;
//import org.railrisk.predictor.service.ServiceInter;
//import org.railrisk.predictor.service.dto.ModelRequestDto;
//import org.railrisk.predictor.service.dto.ModelResponseDto;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//public class ModelController {
//
//    private final ServiceInter serviceInter;
//
//    @GetMapping("/all-station-predicts")
//    public ResponseEntity<List<ModelResponseDto>> getAllStationPredicts() {
//        List<ModelRequestDto> requestList = new ArrayList<>();
//
//        log.info("모든 역의 Weather, Station 객체 가져오기");
//        Weather weather = serviceInter.findWeatherByName(stationName); // 해당 역의 weather 가져오기
//        Station station = serviceInter.findStationByStationName(stationName); // 해당 역의 station 정보 가져오기
//        log.info("모든 역의 Weather, Station 객체 가져오기 완료", stationName);
//
//        // 파싱을 통해 모델 입력에 필요한 것만 선택 후 저장
//        log.info("파싱중");
//        ModelRequestDto modelRequestDto = ModelRequestDtoParser.modelRequestDtoParser(weather, station, stationLine);
//        log.info("파싱완료");
//
//        // 모델에 전달할 리스트에 추가
//        requestList.add(modelRequestDto);
//
//        log.info("모델에 입력값 전달");
//        // 모델에 리스트 넘기기
//        List<ModelResponseDto> response = serviceInter.sendDataToModel(requestList);
//        log.info("모델 결과 전달 받음");
//
//        return ResponseEntity.ok(response);
//    }
//
//
//
//}
