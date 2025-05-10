package org.railrisk.predictor;

import org.junit.jupiter.api.Test;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.repository.WeatherRepositoryInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class WeatherRepositoryTest {

    @Autowired
    WeatherRepositoryInter weatherRepository;

    @Test
    void saveAndLoadWeather() {
        Weather weather = new Weather("202504031200", "108", "3.2", "1013.5", "16.2", "55", "0.0", "0.0", "9.3", "안개_유");
        Weather saved = weatherRepository.save(weather);

        Optional<Weather> found = weatherRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("108", found.get().getStn());
    }
}
