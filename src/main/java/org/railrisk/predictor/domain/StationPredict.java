package org.railrisk.predictor.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 포함 생성자
@Table(name = "station_predict")
public class StationPredict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_name")
    private String stationName;

    private String line;

    @Column(name = "risk_score")
    private Double riskScore;


}

