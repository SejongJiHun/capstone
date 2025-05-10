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
        <div class="weather_container">
          <h3><strong>${stationName}역 날씨 / ${region}</strong></h3>
          <div class="time">${formatTime(w.time)}</div>
          <div class="weather_box1">
            <div class="weather_box2">
              <div>기온: ${w.ta} °C</div>
              <div>일강수량: ${w.rn_day} mm</div>
              <div>습도: ${w.hm} %</div>
              <div>풍속: ${w.ws} m/s</div>
            </div>
            <div class="weather_box3">
              <div>기압: ${w.pa} hPa</div>
              <div>일신적설: ${w.sd_day} cm</div>
              <div>지면온도: ${w.ts} °C</div>
              <div>안개유무: ${w.fog === "1" ? "있음" : "없음"}</div>
            </div>
          </div>
        </div>
    `;
    document.getElementById("weatherInfo").innerHTML = weatherInfoHTML;
}

// 예측 API 호출 (비동기)
async function fetchPredictionData() {
    const stationName = document.getElementById("stationNameInput").value;
    const stationLine = document.getElementById("stationLineInput").value;

    const res = await fetch(`/api/station-predicts?station_name=${encodeURIComponent(stationName)}&station_line=${encodeURIComponent(stationLine)}`);
    if (!res.ok) throw new Error("예측 응답 실패");

    const data = await res.json();
    if (!data || data.length === 0) {
        document.getElementById("predictResult").innerText = "예측 결과 없음";
        return;
    }

    const prediction = data[0];
    const round = (val) => Math.round(val * 100) / 100;

    const resultHTML = `
        <div class="predict_container">
            <h3><strong>${stationName}역 위험도 예측 결과</strong></h3>
            <div>원인 그룹: ${prediction.group}</div>
            <div>세부 원인: ${prediction.cause}</div>
            <div>상세 요인: ${prediction.detail}</div>
            <div><strong>피해 규모 예측</strong></div>
            <div>- 피해액: ${round(prediction.regression.total_damage)} 백만원</div>
            <div>- 사망자 수: ${round(prediction.regression.deaths)} 명</div>
            <div>- 중상자 수: ${round(prediction.regression.severe_injuries)} 명</div>
            <div>- 경상자 수: ${round(prediction.regression.minor_injuries)} 명</div>
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
