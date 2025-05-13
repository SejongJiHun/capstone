package org.railrisk.predictor.repository;

import org.railrisk.predictor.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//구현체는 알아서 생성하고 빈으로 등록해줌
public interface WeatherRepositoryInter extends JpaRepository<Weather, Long> {

    // 특정 시간의 모든 지점 데이터 조회
    List<Weather> findByTime(String time);

    // 특정 지점의 데이터 조회
    Optional<Weather> findByStn(String stn);

    // 특정 지점의 특정 시간 데이터 1건 조회
    Optional<Weather> findByTimeAndStn(String time, String stn);
}
