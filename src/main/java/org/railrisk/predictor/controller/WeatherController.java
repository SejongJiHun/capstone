package org.railrisk.predictor.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.service.ServiceInter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WeatherController {
    private final ServiceInter serviceInter;


    @RequestMapping("/weatherApi")
    public List<Weather> getWeather() throws IOException {
        return serviceInter.fetchAndSaveWeather();
    }

    // ✅ 특정 역(stn_name)의 날씨 조회
    @GetMapping("/search")
    public Weather getWeatherByStationName(@RequestParam("station_name") String stationName) {
        return serviceInter.findWeatherByName(stationName);
    }
}
