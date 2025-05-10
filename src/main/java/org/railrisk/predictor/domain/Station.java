package org.railrisk.predictor.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "station")
@Data
public class Station {

    @Id
    @Column(name = "station_name")
    private String stationName;

    private String address;
    private Integer year1;
    private Integer year2;
    private Integer year3;
    private Integer year4;
    private Float avg;
    private Double lat;
    private Double lon;
    private String stnNum;
    private String stnName;
}
