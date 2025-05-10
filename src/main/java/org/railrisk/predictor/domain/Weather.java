package org.railrisk.predictor.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Data
@Entity // jpa 에서 관리
@Table(name = "origin_weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // 자동생성 기본키

    String time; // 시간
    String stn; // 지점번호
    String ws; // 풍속
    String pa; // 현지기압
    String ta; // 기온
    String hm; //상대습도
    String rn_day; // 위 관측시간까지의 일 강수량. 결측치인 -9.0을 0.0으로 변환 후 저장
    String sd_day; // 일 신적설. 결측치인 -9.0을 0.0으로 변환 후 저장
    String ts; // 지면온도
    String fog; // 안개유무


    public Weather() {
    }

    public Weather(String time, String stn, String ws, String pa, String ta, String hm, String rn_day, String sd_day, String ts,String fog) {
        this.time = time;
        this.stn = stn;
        this.ws = ws;
        this.pa = pa;
        this.ta = ta;
        this.hm = hm;
        this.rn_day = rn_day;
        this.sd_day = sd_day;
        this.ts = ts;
        this.fog = fog;
    }
}
