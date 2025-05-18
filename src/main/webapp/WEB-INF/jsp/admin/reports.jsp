<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    // for navbar
    org.parking.models.User user =
        (org.parking.models.User) session.getAttribute("user");
    boolean admin = (user instanceof org.parking.models.AdminUser);
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ParkMee Reports</title>

  <!-- Bootstrap 5 -->
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
  >

  <!-- Your custom CSS -->
  <link
    href="${pageContext.request.contextPath}/assets/css/header.css?v=1"
    rel="stylesheet"
  >
  <link
    href="${pageContext.request.contextPath}/assets/css/report.css?v=1"
    rel="stylesheet"
  >

  <!-- Chart.js -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
  <!-- full-screen blurred bg -->
  <div class="bg-blur"></div>

  <!-- NAVBAR  -->
  <%@ include file="/WEB-INF/navbar.jsp" %>


  <!-- REPORT PANELS -->
  <div class="container py-5">
    <h2 class="mb-4 text-white">📊 ParkMe Reports</h2>
    <div class="row g-4">

      <!-- Revenue by Booking Type -->
      <div class="col-md-6" style="--delay: 0.1s">
        <div class="card-slab p-4 h-100">
          <h5 class="card-title">Revenue by Booking Type</h5>
          <canvas id="typeChart" height="300"></canvas>
        </div>
      </div>

      <!-- Last 7 Days Revenue -->
      <div class="col-md-6" style="--delay: 0.2s">
        <div class="card-slab p-4 h-100">
          <h5 class="card-title">Last 7 Days Revenue</h5>
          <canvas id="dailyChart" height="300"></canvas>
        </div>
      </div>

    </div>
  </div>

  <!-- Chart.js scripts -->
  <script>
    // --- Data from server ---
    const revenueByType = {
      labels: [
        <c:forEach items="${revenueByType.keySet().toArray()}" var="t" varStatus="st">
          "${t}"<c:if test="${not st.last}">,</c:if>
        </c:forEach>
      ],
      data: [
        <c:forEach items="${revenueByType.values().toArray()}" var="v" varStatus="st">
          ${v}<c:if test="${not st.last}">,</c:if>
        </c:forEach>
      ]
    };

    const dailyLabels = [
      <c:forEach items="${dailyRevenue.keySet().toArray()}" var="d" varStatus="st">
        "${d}"<c:if test="${not st.last}">,</c:if>
      </c:forEach>
    ];
    const dailyData = [
      <c:forEach items="${dailyRevenue.values().toArray()}" var="v" varStatus="st">
        ${v}<c:if test="${not st.last}">,</c:if>
      </c:forEach>
    ];

    // --- Pie chart ---
    new Chart(document.getElementById('typeChart'), {
      type: 'pie',
      data: {
        labels: revenueByType.labels,
        datasets: [{ data: revenueByType.data }]
      },
      options: {
        responsive: true,
        plugins: { legend: { position: 'bottom' } }
      }
    });

    // --- Line chart ---
    new Chart(document.getElementById('dailyChart'), {
      type: 'line',
      data: {
        labels: dailyLabels,
        datasets: [{
          label: 'Revenue (LKR)',
          data: dailyData,
          tension: 0.3,
          fill: true
        }]
      },
      options: {
        responsive: true,
        scales: { y: { beginAtZero: true } }
      }
    });
  </script>

  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
  ></script>
</body>
</html>
