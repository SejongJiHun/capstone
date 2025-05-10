package org.railrisk.predictor.repository;

import org.railrisk.predictor.domain.StationPredict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationPredictRepositoryInter extends JpaRepository<StationPredict, Long> {

    List<StationPredict> findAll();

}
