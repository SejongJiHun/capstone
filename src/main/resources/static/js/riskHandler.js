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
    riskData.forEach(({ stationName, line, riskScore, modelResponseDto }) => {
        const key = makeStationKey(stationName, line);
        const marker = stationLabelLayers[key];

        if (marker) {
            // 예측 결과 정보 추출
            const { group, cause, detail, regression } = modelResponseDto;
            const {
                log_total_damage,
                total_damage,
                deaths,
                severe_injuries,
                minor_injuries
            } = regression || {};

            // 시각화할 HTML 구성
            const html = `
                <div>
                    ${stationName}<br>
                    <b style="color:red">위험도: ${riskScore}</b><br>
                    <small>
                        [${group} - ${cause} - ${detail}]<br>
                        피해액: ${total_damage?.toFixed(1)}원<br>
                        사망: ${deaths}, 중상: ${severe_injuries}, 경상: ${minor_injuries}
                    </small>
                </div>
            `;

            marker.setIcon(L.divIcon({
                className: marker.options.icon.options.className,
                html: html,
                iconSize: [180, 80],
                iconAnchor: [90, -12]
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
