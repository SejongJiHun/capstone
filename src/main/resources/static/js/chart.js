// 🚀 공통 데이터 가져오기
fetch("../data/일평균채움_최종.json")
    .then(res => res.json())
    .then(data => {
      drawChartA(data);
      drawChartD(data);
      drawChartE(data);
      drawChartF(data);
    });

function drawChartA(data) {
  const counts = {};
  data.forEach(item => {
    const type = item["철도구분"];
    if (type) counts[type] = (counts[type] || 0) + 1;
  });

  const filtered = Object.entries(counts)
      .filter(([_, value]) => value > 1)
      .sort((a, b) => b[1] - a[1]);

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
        backgroundColor: "rgba(54, 162, 235, 0.6)",
        borderColor: "rgba(54, 162, 235, 1)",
        hoverBackgroundColor: "rgba(54, 162, 235, 0.9)",
        hoverBorderColor: "rgba(54, 162, 235, 1)",
        borderWidth: 1,
        borderRadius: 6,
        barThickness: 32
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      interaction: {
        mode: 'index',
        axis: 'x',
        intersect: false
      },
      animation: {
        onComplete: () => chart.update()
      },
      plugins: {
        legend: { display: true, position: "top" },
        datalabels: {
          color: "#111",
          font: { weight: "bold", size: 13 },
          anchor: "end",
          align: function (context) {
            const chartTop = context.chart.chartArea.top;
            const barY = context.chart.getDatasetMeta(context.datasetIndex).data[context.dataIndex].y;
            return barY < chartTop + 20 ? "start" : "end";
          },
          offset: 4,
          formatter: v => v
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          ticks: { stepSize: 500 },
          grid: { color: "#eee" }
        },
        x: {
          grid: { display: false },
          ticks: {
            font: { size: 14, weight: "bold" },
            color: "#333"
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

  const dayOrder = ["월", "화", "수", "목", "금", "토", "일"];
  const sortedEntries = dayOrder.map(day => [day, counts[day] || 0]);
  const labels = sortedEntries.map(([day]) => day);
  const values = sortedEntries.map(([_, count]) => count);

  const ctx = document.getElementById("chartD").getContext("2d");
  const chart = new Chart(ctx, {
    type: "bar",
    data: {
      labels,
      datasets: [{
        label: "요일별 사고 건수",
        data: values,
        backgroundColor: "rgba(241, 151, 160, 0.6)",
        borderColor: "rgba(241, 151, 160, 1)",
        hoverBackgroundColor: "rgba(241, 151, 160, 0.9)",
        hoverBorderColor: "rgba(241, 151, 160, 1)",
        borderWidth: 1,
        borderRadius: 6,
        barThickness: 32
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      interaction: {
        mode: 'index',
        axis: 'x',
        intersect: false
      },
      animation: {
        onComplete: () => chart.update()
      },
      plugins: {
        legend: { display: true, position: "top" },
        datalabels: {
          color: "#111",
          font: { weight: "bold", size: 13 },
          anchor: "end",
          align: function (context) {
            const top = context.chart.chartArea.top;
            const y = context.chart.getDatasetMeta(context.datasetIndex).data[context.dataIndex].y;
            return y < top + 20 ? "start" : "end";
          },
          offset: 0,
          formatter: v => v
        }
      },
      scales: {
        y: { beginAtZero: true, grid: { color: "#eee" } },
        x: {
          grid: { display: false },
          ticks: {
            font: { size: 14, weight: "bold" },
            color: "#333"
          }
        }
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

  const filtered = Object.entries(counts)
      .filter(([_, value]) => value > 4)
      .sort((a, b) => b[1] - a[1]);

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
        backgroundColor: "rgba(249, 199, 79, 0.6)",
        borderColor: "rgba(249, 199, 79, 1)",
        hoverBackgroundColor: "rgba(249, 199, 79, 0.9)",
        hoverBorderColor: "rgba(249, 199, 79, 1)",
        borderWidth: 1,
        borderRadius: 6,
        barThickness: 32
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      interaction: {
        mode: 'index',
        axis: 'x',
        intersect: false
      },
      animation: {
        onComplete: () => chart.update()
      },
      plugins: {
        legend: { display: true, position: "top" },
        datalabels: {
          color: "#111",
          font: { weight: "bold", size: 13 },
          anchor: "end",
          align: function (context) {
            const top = context.chart.chartArea.top;
            const y = context.chart.getDatasetMeta(context.datasetIndex).data[context.dataIndex].y;
            return y < top + 20 ? "start" : "end";
          },
          offset: 0,
          formatter: v => v
        }
      },
      scales: {
        y: { beginAtZero: true, grid: { color: "#eee" } },
        x: {
          grid: { display: false },
          ticks: {
            font: { size: 14, weight: "bold" },
            color: "#333"
          }
        }
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

  const filtered = Object.entries(counts)
      .filter(([_, value]) => value > 9)
      .sort((a, b) => b[1] - a[1]);

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
        backgroundColor: "rgba(151, 212, 148, 0.6)",
        borderColor: "rgba(151, 212, 148, 1)",
        hoverBackgroundColor: "rgba(151, 212, 148, 0.9)",
        hoverBorderColor: "rgba(151, 212, 148, 1)",
        borderWidth: 1,
        borderRadius: 6,
        barThickness: 32
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      interaction: {
        mode: 'index',
        axis: 'x',
        intersect: false
      },
      animation: {
        onComplete: () => chart.update()
      },
      plugins: {
        legend: { display: true, position: "top" },
        datalabels: {
          color: "#111",
          font: { weight: "bold", size: 13 },
          anchor: "end",
          align: function (context) {
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
            minRotation: 40,
            font: { size: 14, weight: "bold" },
            color: "#333"
          },
          grid: { display: false }
        },
        y: { beginAtZero: true, grid: { color: "#eee" } }
      }
    },
    plugins: [ChartDataLabels]
  });
}
