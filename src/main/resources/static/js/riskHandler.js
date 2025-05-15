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

    riskData.forEach(({ stationName, line, riskScore, modelResponseDto }) => {
        const key = makeStationKey(stationName, line);
        const marker = stationLabelLayers[key];

        if (marker) {
            console.log(`✅ 매칭 성공: ${stationName} (${line}) → ${key}`);

            // 위험도 색상 결정
            let iconColor;
            if (riskScore < 30) {
                iconColor = "green";
            } else if (riskScore < 70) {
                iconColor = "orange";
            } else {
                iconColor = "red";
            }

            // 예측 결과 정보 추출
            const { group, cause, detail, regression } = modelResponseDto;
            const {
                log_total_damage,
                total_damage,
                deaths,
                severe_injuries,
                minor_injuries
            } = regression || {};

            // 마커 HTML 구성
            const html = `
                <div>
                    <div style="display: flex; flex-direction: column; align-items: center;">
                        <i class="fa-solid fa-bell" style="
                            color: ${iconColor};
                            font-size: 1.4em;
                            text-shadow: 1px 1px 2px black;
                            margin-bottom: 2px;
                        "></i>
                        <b style="color:red">위험도: ${riskScore}</b>
                    </div>
                    <div style="margin-top: 23px;">
                        ${stationName}<br>
                        <small>
                            [${group} - ${cause} - ${detail}]<br>
                            피해액: ${total_damage?.toFixed(1)}원<br>
                            사망: ${deaths}, 중상: ${severe_injuries}, 경상: ${minor_injuries}
                        </small>
                    </div>
                </div>
            `;

            // 마커 아이콘 설정
            marker.setIcon(L.divIcon({
                className: marker.options.icon.options.className,
                html: html,
                iconSize: [200, 90],
                iconAnchor: [100, 45]
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
