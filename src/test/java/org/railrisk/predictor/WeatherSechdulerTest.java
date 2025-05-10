package org.railrisk.predictor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.railrisk.predictor.api.OriginWeatherApi;
import org.railrisk.predictor.domain.Weather;
import org.railrisk.predictor.repository.WeatherRepositoryInter;
import org.railrisk.predictor.schedule.WeatherScheduler;
import org.railrisk.predictor.service.ServiceInter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WeatherSechdulerTest {

    // 가짜 객체(Mock) 선언
//    private OriginWeatherApi apiClient;
//    private WeatherRepositoryInter weatherRepository;
//    private ServiceInter serviceInter;
//
//    @BeforeEach
//    void setUp() {
//        // 각 테스트 실행 전, Mock 객체 생성
//        apiClient = mock(OriginWeatherApi.class);
//        weatherRepository = mock(WeatherRepositoryInter.class);
//
//        // WeatherScheduler에 Mock 주입
//        scheduler = new WeatherScheduler(apiClient, weatherRepository);
//    }
//
//    @Test
//    void fetchAndSaveWeather_should_parse_and_save_weather_data() {
//        // ✅ 테스트 목적: 날씨 데이터를 잘 파싱하고 DB에 저장하는지 검증
//
//        // given: API 응답처럼 보이는 가짜 문자열 (필드는 37개 이상 필요!)
//        String fakeApiResponse = "202504031200 108 0.0 2.3 0.0 0.0 0.0 1012.3 0.0 0.0 0.0 0.0 16.5 0.0 60 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 8.3";
//        when(apiClient.getRawWeatherText()).thenReturn(fakeApiResponse);
//
//        // when: 실제 메서드 실행
//        List<Weather> result = scheduler.fetchAndSaveWeather();
//
//        // then: saveAll()이 한번 호출됐는지 확인하고, 그 안에 저장된 데이터 검증
//        ArgumentCaptor<List<Weather>> captor = ArgumentCaptor.forClass(List.class);
//        verify(weatherRepository, times(1)).saveAll(captor.capture());
//
//        List<Weather> savedList = captor.getValue(); // 실제 저장된 값 꺼내기
//
//        // 저장된 데이터 개수와 필드 값 확인
//        assertThat(savedList).hasSize(1);
//        assertThat(savedList.get(0).getStn()).isEqualTo("108");
//
//        // 반환 결과도 같은지 확인
//        assertThat(result).hasSize(1);
//    }
//
//    @Test
//    void fetchAndSaveWeather_should_do_nothing_when_empty_data() {
//        // ✅ 테스트 목적: 저장할 데이터가 없을 때 saveAll이 호출되지 않아야 함
//
//        // given: 무의미한 문자열만 들어있는 응답 (파싱 대상 없음)
//        when(apiClient.getRawWeatherText()).thenReturn("some header line\nfooter or comment line");
//
//        // when
//        List<Weather> result = scheduler.fetchAndSaveWeather();
//
//        // then: 저장 메서드가 호출되지 않아야 함
//        verify(weatherRepository, never()).saveAll(any());
//
//        // 결과 리스트도 비어있어야 함
//        assertThat(result).isEmpty();
//    }
}
