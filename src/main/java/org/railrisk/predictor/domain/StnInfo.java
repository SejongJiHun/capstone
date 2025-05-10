package org.railrisk.predictor.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="stn_info")
public class StnInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stn;
    private String name;
    private String address;
    private Double lat;
    private Double lon;

    public StnInfo() {
    }

    public StnInfo(Long id, String stn, String name, String address, Double lat, Double lon) {
        this.id = id;
        this.stn = stn;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }
}
