package org.railrisk.predictor.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Component
public class OriginWeatherApi {

    private static final String API_URL = "https://apihub.kma.go.kr/api/typ01/url/kma_sfctm2.php?tm=&stn=0&help=1&authKey=cmYhknfjRPqmIZJ340T68Q";

    public String getRawWeatherText() {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-KR"));
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }
            in.close();
            con.disconnect();
        } catch (Exception e) {
            log.error("❌ 날씨 API 호출 실패", e);
            return "";
        }

        return response.toString();
    }

}
