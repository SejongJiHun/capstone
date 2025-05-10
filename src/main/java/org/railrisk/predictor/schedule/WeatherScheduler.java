package org.railrisk.predictor.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.railrisk.predictor.api.OriginWeatherApi;
import org.railrisk.predictor.domain.Station;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.parser.WeatherParser;
import org.railrisk.predictor.repository.StationRepositoryInter;
import org.railrisk.predictor.repository.WeatherRepositoryInter;
import org.railrisk.predictor.service.ServiceInter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherScheduler {
    private final ServiceInter serviceInter;


    // 스케쥴러
    @Scheduled(cron = "0 2 0-23 * * *") // 초, 분, 시, 월, 일, 요일
    @Transactional
    public void Scheduled() {
        log.info("🌦️ [스케줄러] 시작");
        List<Weather> result = serviceInter.fetchAndSaveWeather(); // 날씨API로 모든 지역의 날씨 데이터 가져오기

        //플라스크에 데이터 전달
        log.info("🌦️ [스케줄러] 완료. 저장 건수: {}", result.size());
    }

}
