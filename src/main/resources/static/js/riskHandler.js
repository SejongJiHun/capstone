export function requestRiskScores(stationData, callback) {
    fetch('http://localhost:8080/api/riskScore', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(stationData)
    })
        .then(res => {
            if (!res.ok) throw new Error("서버 에러: " + res.status);
            return res.json();
        })
        .then(data => {
            console.log("✅ 응답 확인:", data);
            callback(data);
        })
        .catch(err => {
            alert('위험도 요청 실패: ' + err);
        });
}

export function updateRiskLabels(riskData, stationLabelLayers) {
    console.log("🔄 업데이트할 위험도 데이터:", riskData);

    riskData.forEach(({ stationName, line, modelResponseDto }) => {
        const key = makeStationKey(stationName, line);
        const marker = stationLabelLayers[key];

        if (marker) {
            const { predicted_cause, damage_risk, class_risk, combined_risk } = modelResponseDto;

            let iconColor = "#22c55e"; // 매우 낮음 (초록)

            if (combined_risk > 1.5 && combined_risk < 3) {
                iconColor = "#facc15"; // 보통 (노랑)
            } else if (combined_risk >= 3 && combined_risk < 4) {
                iconColor = "#f97316"; // 높음 (주황)
            } else if (combined_risk >= 4) {
                iconColor = "#dc2626"; // 매우 높음 (빨강)
            }

            const html = `
              <div style="display: flex; flex-direction: column; align-items: center; margin-top: 20px; min-width: 150px;">
  <i class="fa-solid fa-bell" style="
    color: ${iconColor};
    font-size: 1.4em;
    text-shadow: 1px 1px 2px black;
    margin-bottom: 2px;
  "></i>
  
  
  <button
    type="button"
    style="
      font-size: 13px;
      text-align: center;
      background-color: #f0f0f0;
      color: #333;
      border: 1px solid #000;
      border-radius: 6px;
      padding: 4px 10px;
      margin-bottom: 4px;
      cursor: default;
      pointer-events: none; 
    ">
    ${stationName}
  </button>

  <div style="font-size: 14px; font-weight: 550; text-align: center; white-space: nowrap;">
    예측 사고 원인: ${predicted_cause}
  </div>
  
  <b style="color:red; font-size: 14px;">
    최종 위험도: ${combined_risk}
  </b>
</div>

            `;

            marker.setIcon(L.divIcon({
                className: marker.options.icon.options.className,
                html: html,
                iconSize: [100, 85],
                iconAnchor: [45, 0] // 점 기준 상단에 기준점을 둠
            }));

            // 툴팁 비활성화 (요청사항 반영)
            // marker.unbindTooltip();
            // marker.bindTooltip(...);
        } else {
            console.warn(`❌ 매칭 실패: ${stationName}, ${line} → ${key}`);
        }
    });
}

export function makeStationKey(name, line) {
    return `${normalizeStationName(line)}__${normalizeStationName(name)}`;
}

function normalizeStationName(name) {
    return name?.trim().replace(/\s+/g, '') || '';
}
