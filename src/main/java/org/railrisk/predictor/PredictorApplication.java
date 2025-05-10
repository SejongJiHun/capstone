package org.railrisk.predictor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // @Scheduled사용하려면 추가 필수
@SpringBootApplication
public class PredictorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PredictorApplication.class, args);
	}

}
