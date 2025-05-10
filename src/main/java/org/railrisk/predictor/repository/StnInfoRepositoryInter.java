package org.railrisk.predictor.repository;

import org.railrisk.predictor.domain.StnInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StnInfoRepositoryInter extends JpaRepository<StnInfo, Long> {

    Optional<StnInfo> findByName(String StationName);

}
