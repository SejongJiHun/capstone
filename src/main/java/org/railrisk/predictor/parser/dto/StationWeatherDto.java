package org.railrisk.predictor.parser.dto;

import lombok.Data;
import org.railrisk.predictor.domain.Station;
import org.railrisk.predictor.domain.Weather;

@Data
public class StationWeatherDto {
    private Station station;
    private Weather weather;

    public StationWeatherDto() {
    }

    public StationWeatherDto(Station station, Weather weather) {
        this.station = station;
        this.weather = weather;
    }
}
