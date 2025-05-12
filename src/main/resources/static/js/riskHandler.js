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

// ✅ 위험도 텍스트를 지도에 표시
export function updateRiskLabels(riskData, stationLabelLayers) {
    riskData.forEach(({ stationName, line, riskScore }) => {
        const key = makeStationKey(stationName, line);
        const marker = stationLabelLayers[key];
        if (marker) {
            marker.setIcon(L.divIcon({
                className: marker.options.icon.options.className,
                html: `<div>${stationName}<br><b style="color:red">${riskScore}</b></div>`,
                iconSize: [60, 30],
                iconAnchor: [30, -12]
            }));
        } else {
            console.warn(`❌ 매칭 실패: ${stationName}, ${line} → ${key}`);
        }
    });
}

// ✅ 역명 + 노선 조합 키 생성 함수
export function makeStationKey(name, line) {
    return `${normalizeStationName(line)}__${normalizeStationName(name)}`;
}

// ✅ 문자열 정제용 헬퍼 함수 (공백만 제거)
function normalizeStationName(name) {
    return name?.trim().replace(/\s+/g, '') || '';
}
