// 시간 포맷 변환 함수
function formatTime(rawTime) {
    if (!rawTime || rawTime.length < 12) return rawTime;
    const year = rawTime.slice(0, 4);
    const month = rawTime.slice(4, 6);
    const day = rawTime.slice(6, 8);
    const hour = rawTime.slice(8, 10);
    const minute = rawTime.slice(10, 12);
    const second = rawTime.slice(12, 14) || "00";
    return `${year}-${month}-${day} / ${hour}:${minute}:${second}`;
}

// 날씨 API 호출 (비동기)
async function fetchWeatherData() {
    const stationName = document.getElementById("stationNameInput").value;

    const res = await fetch(`/api/station-weathers?station_name=${encodeURIComponent(stationName)}`);
    if (!res.ok) throw new Error("날씨 응답 실패");

    const data = await res.json();
    const w = data.weather;
    const region = data.station;  // 지역명 (stnName)

    const weatherInfoHTML = `
  <div class="weather-card">
    <div class="weather-header">
      <h3>${stationName}역 날씨</h3>
      <span class="weather-region">${region} (${formatTime(w.time)})</span>
    </div>
    <div class="weather-content">
      <div class="weather-column">
        <div>🌡️ 기온: ${w.ta} °C</div>
        <div>🌧️ 일강수량: ${w.rn_day} mm</div>
        <div>💧 습도: ${w.hm} %</div>
        <div>🌬️ 풍속: ${w.ws} m/s</div>
      </div>
      <div class="weather-column">
        <div>📈 기압: ${w.pa} hPa</div>
        <div>❄️ 일신적설: ${w.sd_day} cm</div>
        <div>🌎 지면온도: ${w.ts} °C</div>
        <div>🌫️ 안개: ${w.fog === "1" ? "있음" : "없음"}</div>
      </div>
    </div>
  </div>
`;
    document.getElementById("weatherInfo").innerHTML = weatherInfoHTML;
}

// 예측 API 호출 (비동기)
async function fetchPredictionData() {
    const stationName = document.getElementById("stationNameInput").value;
    const railType = document.getElementById("railTypeSelect").value;
    const stationLine = document.getElementById("stationLineInput").value;

    if (!stationName || !railType || !stationLine) {
        alert("역명, 철도구분, 노선을 모두 입력해주세요.");
        return;
    }

    const res =
        await fetch(`/api/station-predicts?station_name=${encodeURIComponent(stationName)}&rail_type=${encodeURIComponent(railType)}&station_line=${encodeURIComponent(stationLine)}`);
    if (!res.ok) throw new Error("예측 응답 실패");

    const data = await res.json();
    console.log("📦 Spring 응답:", data);


    if (!data || data.length === 0) {
        document.getElementById("predictResult").innerText = "예측 결과 없음";
        return;
    }

    const prediction = data[0];
    console.log(prediction.predicted_cause);  // 이게 undefined면 key 이름이 문제
    console.log(prediction.damage_risk);
    console.log(prediction.class_risk);
    console.log(prediction.combined_risk);


    const round = (val) => Math.round(val * 100) / 100;

    const resultHTML = `
  <div class="predict-card">
    <h3>${stationName}역 위험도 예측 결과</h3>
    <div class="predict-row"><span>예측 사고 원인:</span> <strong>${prediction.predicted_cause}</strong></div>
    <div class="predict-row"><span>피해액 위험도[A](1~5):</span> ${round(prediction.damage_risk)}</div>
    <div class="predict-row"><span>사고 원인 위험도[B](0~3):</span> ${round(prediction.class_risk)}</div>
    <div class="predict-row final"><span>최종 위험도(A × 0.6 + B × 0.4):</span> ${round(prediction.combined_risk)}</div>
  </div>
`;
    document.getElementById("predictResult").innerHTML = resultHTML;

}

// 전체 조회 핸들러 (독립적 예외 처리)
async function handleQuery() {
    const errors = [];

    try {
        await fetchWeatherData();
    } catch (err) {
        console.error("❌ 날씨 오류:", err);
        errors.push("날씨");
    }

    try {
        await fetchPredictionData();
    } catch (err) {
        console.error("❌ 예측 오류:", err);
        errors.push("예측");
    }

    if (errors.length > 0) {
        alert(`⚠ 일부 데이터를 불러오지 못했습니다: ${errors.join(", ")}`);
    }
}
