package org.railrisk.predictor.parser;


import org.railrisk.predictor.domain.Weather;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class WeatherParser {
    public static List<Weather> parse(String rawText) {
        List<Weather> list = new ArrayList<>();
        String[] lines = rawText.split("\n");

        for (String i : lines) {
            if (i.matches("^\\d{12}.*")) {
                String[] tokens = i.trim().split("\\s+");
                String time = tokens[0];
                String stn = tokens[1];
                String ws = tokens[3];
                String pa = tokens[7];
                String ta = tokens[11];
                String hm = tokens[13];
                String rn_day = sanitize(tokens[16]);
                String sd_day = sanitize(tokens[20]);
                String wp = tokens[23]; // wp. 안개에 필요
                String vs = tokens[32]; // vs. 안개에 필요
                String ts = tokens[36];

                //안개판단
                String fog = check_fog(wp, vs);

                Weather weather = new Weather(time, stn, ws, pa, ta, hm, rn_day, sd_day, ts, fog);
                list.add(weather);
            }
        }

        return list;
    }

    private static String sanitize(String raw) {
        try {
            double value = Double.parseDouble(raw);
            return value == -9.0 ? "0.0" : raw;
        } catch (NumberFormatException e) {
            return "0.0";
        }
    }

    private static String check_fog(String wp, String vs) {
        String[] fogCodes = {"40", "41", "45", "49", "72", "74", "75"};

        // WP 안에 안개 관련 코드가 포함되어 있는지 확인
        for (String code : fogCodes) {
            if (wp.contains(code)) {
                return "안개_유";
            }
        }

        try {
            int vsValue = Integer.parseInt(vs); // VS는 10m 단위
            if (vsValue <= 100) { // 1000m 이하
                return "안개_유";
            }
        } catch (NumberFormatException e) {
            // VS 값이 비정상이라면 무시하고 "안개_무" 처리
        }

        return "안개_무";
    }

}
