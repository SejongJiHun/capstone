
// 🚀 공통 데이터 가져오기
fetch("../data/일평균채움_최종.json")
  .then(res => res.json())
  .then(data => {
    drawChartA(data);
    drawChartD(data);
    drawChartE(data);
    drawChartF(data);
    drawChartG(data);
  });

// 📊 A. 철도구분별 사고 건수
function drawChartA(data) {
  const counts = {};
  data.forEach(item => {
    const type = item["철도구분"];
    if (type) counts[type] = (counts[type] || 0) + 1;
  });

  const filtered = Object.entries(counts).filter(([_, value]) => value > 1);
  const labels = filtered.map(([label]) => label);
  const values = filtered.map(([_, value]) => value);

  const trace = {
    type: 'bar',
    x: labels,
    y: values,
    marker: { color: '#6ca0c9' },
    text: labels,  // ⬅ 라벨 전체 텍스트를 text로 저장
    textposition: 'none',  // 막대 위 숫자는 숨기고 툴팁만 보여줄 경우 'none'
    hoverinfo: 'text+y',   // ⬅ 툴팁에 라벨(text) + 값(y) 보여주기
    hovertemplate: '<b>%{x}</b><br>건수: %{y}건<extra></extra>'
  };

  Plotly.newPlot("chartA", [trace], {
    title: "2006~2023 철도구분별 사고 건수",
    margin: { t: 40, b: 60 },
    xaxis: {
      title: "철도 구분",
      tickangle: -30  // 라벨 겹치면 살짝 기울여서 보기 좋게
    },
    yaxis: { title: "건수", zeroline: false },
    responsive: true
  });
}


// 📊 D. 요일별 사고 건수
function drawChartD(data) {
  const counts = {};
  data.forEach(item => {
    const day = item["요일"];
    if (day) counts[day] = (counts[day] || 0) + 1;
  });

  const labels = Object.keys(counts);
  const values = Object.values(counts);

  const trace = {
    type: 'bar',
    x: labels,
    y: values,
    marker: { color: '#f197a0' },
    text: values.map(v => `${v}건`),
    textposition: 'auto',
    hovertemplate: '%{x}<br>건수: %{y}<extra></extra>'
  };

  Plotly.newPlot("chartD", [trace], {
    title: "요일별 사고 건수",
    margin: { t: 40, b: 60 },
    xaxis: { title: "요일" },
    yaxis: { title: "건수", zeroline: false },
    responsive: true
  });
}

// 📊 E. 근본원인별 그룹별 사고 건수
function drawChartE(data) {
  const counts = {};
  data.forEach(item => {
    const cause = item["근본원인별 그룹"];
    if (cause) counts[cause] = (counts[cause] || 0) + 1;
  });

  const filtered = Object.entries(counts).filter(([_, value]) => value > 4);
  const labels = filtered.map(([label]) => label);
  const values = filtered.map(([_, value]) => value);

  const trace = {
    type: 'bar',
    x: values,
    y: labels,
    orientation: 'h',
    marker: { color: '#f9c74f' },
    text: values.map(v => `${v}건`),
    textposition: 'auto',
    hovertemplate: '%{y}<br>건수: %{x}<extra></extra>'
  };

  Plotly.newPlot("chartE", [trace], {
    title: "근본원인 그룹별 사고 건수",
    margin: { t: 40, l: 150 },
    xaxis: { title: "건수" },
    yaxis: { automargin: true },
    responsive: true
  });
}

// 📊 F. 근본원인별 원인별 사고 건수
function drawChartF(data) {
  const counts = {};
  data.forEach(d => {
    const reason = d["근본원인별 원인"];
    if (reason) counts[reason] = (counts[reason] || 0) + 1;
  });

  const filtered = Object.entries(counts).filter(([_, value]) => value > 0);
  const labels = filtered.map(([label]) => label);
  const values = filtered.map(([_, value]) => value);

  const trace = {
    type: 'bar',
    x: values,
    y: labels,
    orientation: 'h',
    marker: { color: '#97d494' },
    text: values.map(v => `${v}건`),
    textposition: 'auto',
    hovertemplate: '%{y}<br>건수: %{x}<extra></extra>'
  };

  Plotly.newPlot("chartF", [trace], {
    title: "근본원인별 사고 건수",
    margin: { t: 40, l: 160 },
    xaxis: { title: "건수" },
    yaxis: { automargin: true },
    responsive: true
  });
}

// 📊 G. 근본원인 상세별 사고 건수
function drawChartG(data) {
  const counts = {};
  data.forEach(d => {
    const detail = d["근본원인별 상세"];
    if (detail) counts[detail] = (counts[detail] || 0) + 1;
  });

  const filtered = Object.entries(counts).filter(([_, value]) => value > 0);
  const labels = filtered.map(([label]) => label);
  const values = filtered.map(([_, value]) => value);

  const trace = {
    type: 'bar',
    x: values,
    y: labels,
    orientation: 'h',
    marker: { color: '#f9844a' },
    text: values.map(v => `${v}건`),
    textposition: 'auto',
    hovertemplate: '%{y}<br>건수: %{x}<extra></extra>'
  };

  Plotly.newPlot("chartG", [trace], {
    title: "근본원인 상세별 사고 건수",
    margin: { t: 40, l: 180 },
    xaxis: { title: "건수" },
    yaxis: { automargin: true },
    responsive: true
  });
}
