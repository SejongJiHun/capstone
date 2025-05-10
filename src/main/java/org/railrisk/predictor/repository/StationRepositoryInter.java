package org.railrisk.predictor.repository;

import org.railrisk.predictor.domain.Station;
import org.railrisk.predictor.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//구현체는 알아서 생성하고 빈으로 등록해줌
public interface StationRepositoryInter extends JpaRepository<Station, String> {
    Optional<Station> findByStationName(String stationName);


}
