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


function drawChartA(data) {
  const counts = {};

  data.forEach(item => {
    const type = item["철도구분"];
    if (type) counts[type] = (counts[type] || 0) + 1;
  });

  const filtered = Object.entries(counts).filter(([_, value]) => value > 1);
  const labels = filtered.map(([label]) => label);
  const values = filtered.map(([_, value]) => value);

  const ctx = document.getElementById("chartA").getContext("2d");

  const chart = new Chart(ctx, {
    type: "bar",
    data: {
      labels,
      datasets: [{
        label: "2006~2023 철도구분별 사고 건수",
        data: values,
        backgroundColor: "#6ca0c9"
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      animation: {
        onComplete: () => {
          // 애니메이션 끝나고 나서 재계산
          chart.update();
        }
      },
      plugins: {
        legend: {
          display: true,
          position: "top"
        },

        datalabels: {
          color: "#000",
          font: {
            weight: "bold",
            size: 14
          },
          anchor: "end",
          align: function (context) {
            const chartTop = context.chart.chartArea.top;
            const barY = context.chart.getDatasetMeta(context.datasetIndex).data[context.dataIndex].y;
            // 너무 위까지 올라오면 아래로 숫자 표시
            return barY < chartTop + 20 ? "start" : "end";
          },
          offset: 4,
          formatter: function (value) {
            return value;
          }
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            stepSize: 500
          }
        }
      }
    },
    plugins: [ChartDataLabels]
  });
}




function drawChartD(data) {
  const counts = {};
  data.forEach(item => {
    const day = item["요일"];
    if (day) counts[day] = (counts[day] || 0) + 1;
  });

  const ctx = document.getElementById("chartD").getContext("2d");
  const chart = new Chart(ctx, {
    type: "bar",
    data: {
      labels: Object.keys(counts),
      datasets: [{
        label: "요일별 사고 건수",
        data: Object.values(counts),
        backgroundColor: "#f197a0"
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      animation: {
        onComplete: () => chart.update()
      },
      plugins: {
        legend: { display: true, position: "top" },
        datalabels: {
          color: "#000",
          font: { weight: "bold", size: 14 },
          anchor: "end",
          align: function(context) {
            const top = context.chart.chartArea.top;
            const y = context.chart.getDatasetMeta(context.datasetIndex).data[context.dataIndex].y;
            return y < top + 20 ? "start" : "end";
          },
          offset: 0,
          formatter: v => v
        }
      },
      scales: {
        y: { beginAtZero: true }
      }
    },
    plugins: [ChartDataLabels]
  });
}





function drawChartE(data) {
  const counts = {};
  data.forEach(item => {
    const cause = item["근본원인별 그룹"];
    if (cause) counts[cause] = (counts[cause] || 0) + 1;
  });

  const filtered = Object.entries(counts).filter(([_, value]) => value > 4);
  const labels = filtered.map(([label]) => label);
  const values = filtered.map(([_, value]) => value);

  const ctx = document.getElementById("chartE").getContext("2d");
  const chart = new Chart(ctx, {
    type: "bar",
    data: {
      labels,
      datasets: [{
        label: "근본원인그룹별 사고 건수",
        data: values,
        backgroundColor: "#f9c74f"
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      animation: {
        onComplete: () => chart.update()
      },
      plugins: {
        legend: { display: true, position: "top" },
        datalabels: {
          color: "#000",
          font: { weight: "bold", size: 14 },
          anchor: "end",
          align: function(context) {
            const top = context.chart.chartArea.top;
            const y = context.chart.getDatasetMeta(context.datasetIndex).data[context.dataIndex].y;
            return y < top + 20 ? "start" : "end";
          },
          offset: 0,
          formatter: v => v
        }
      },
      scales: {
        y: { beginAtZero: true }
      }
    },
    plugins: [ChartDataLabels]
  });
}




function drawChartF(data) {
  const counts = {};
  data.forEach(d => {
    const reason = d["근본원인별 원인"];
    if (reason) counts[reason] = (counts[reason] || 0) + 1;
  });

//발생건수 10건 이상만 표시
  const filtered = Object.entries(counts).filter(([_, value]) => value > 9);
  const labels = filtered.map(([label]) => label);
  const values = filtered.map(([_, value]) => value);

  const ctx = document.getElementById("chartF").getContext("2d");
  const chart = new Chart(ctx, {
    type: "bar",
    data: {
      labels,
      datasets: [{
        label: "근본원인별 사고 건수",
        data: values,
        backgroundColor: "#97d494"
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      animation: {
        onComplete: () => chart.update()
      },
      plugins: {
        legend: { display: true, position: "top" },

        datalabels: {
          color: "#000",
          font: { weight: "bold", size: 14 },
          anchor: "end",
          align: function(context) {
            const top = context.chart.chartArea.top;
            const y = context.chart.getDatasetMeta(context.datasetIndex).data[context.dataIndex].y;
            return y < top + 20 ? "start" : "end";
          },
          offset: 0,
          formatter: v => v
        }
      },
      scales: {
        x: {
          ticks: {
            callback: function(value) {
              const label = this.getLabelForValue(value);
              return label.length > 4 ? label.slice(0, 4) + "…" : label;
            },
            autoSkip: false,
            maxRotation: 40,
            minRotation: 40
          }
        },
        y: { beginAtZero: true }
      }
    },
    plugins: [ChartDataLabels]
  });
}


// 상세
function drawChartG(data) {
  const counts = {};
  data.forEach(d => {
    const detail = d["근본원인별 상세"];
    if (detail) counts[detail] = (counts[detail] || 0) + 1;
  });

  //발생건수 10건 이상만 표시
  const filtered = Object.entries(counts).filter(([_, value]) => value > 9);
  const labels = filtered.map(([label]) => label);
  const values = filtered.map(([_, value]) => value);

  const ctx = document.getElementById("chartG").getContext("2d");
  const chart = new Chart(ctx, {
    type: "bar",
    data: {
      labels,
      datasets: [{
        label: "근본원인상세별 사고 건수",
        data: values,
        backgroundColor: "#f9844a"
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      animation: {
        onComplete: () => chart.update()
      },
      plugins: {
        legend: { display: true, position: "top" },
        datalabels: {
          color: "#000",
          font: { weight: "bold", size: 12 },
          anchor: "end",
          align: function(context) {
            const top = context.chart.chartArea.top;
            const y = context.chart.getDatasetMeta(context.datasetIndex).data[context.dataIndex].y;
            return y < top + 20 ? "start" : "end";
          },
          offset: 0,
          formatter: v => v
        }
      },
      scales: {
        x: {
          ticks: {
            callback: function(value) {
              const label = this.getLabelForValue(value);
              return label.length > 4 ? label.slice(0, 4) + "…" : label;
            },
            autoSkip: false,
            maxRotation: 40,
            minRotation: 40
          }
        },
        y: { beginAtZero: true }
      }
    },
    plugins: [ChartDataLabels]
  });
}


