<%@page contentType="text/html;charset=UTF-8" session="true"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%
    org.parking.models.User user =
        (org.parking.models.User) session.getAttribute("user");
    boolean admin = (user instanceof org.parking.models.AdminUser);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>

    <!-- Bootstrap -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">

    <!-- 1) header overrides first -->
    <link
      href="${pageContext.request.contextPath}/assets/css/header.css?v=1"
      rel="stylesheet">

    <!-- 2) then blurred-bg + frosted-glass + slide-up -->
    <link
      href="${pageContext.request.contextPath}/assets/css/dashboard.css?v=1"
      rel="stylesheet">
</head>
<body>

  <!-- full-screen blurred bg -->
  <div class="bg-blur"></div>

  <!-- NAVBAR -->
  <%@ include file="/WEB-INF/navbar.jsp" %>

  <!-- CONTENT -->
  <div class="container">
    <div class="row g-4 mb-4">
      <div class="col-6 col-lg-4" style="--delay: 0.1s">
        <a class="card-slab p-4 d-block text-decoration-none h-100" href="profile">
          <h5 class="text-white">My Profile</h5>
        </a>
      </div>
      <div class="col-6 col-lg-4" style="--delay: 0.2s">
        <a class="card-slab p-4 d-block h-100" href="vehicle/list">
          <h5 class="text-white">My Vehicles</h5>
        </a>
      </div>
      <div class="col-6 col-lg-4" style="--delay: 0.3s">
        <a class="card-slab p-4 d-block h-100" href="bookings">
          <h5 class="text-white">Bookings</h5>
        </a>
      </div>
      <div class="col-6 col-lg-4" style="--delay: 0.4s">
        <a class="card-slab p-4 d-block h-100" href="payments">
          <h5 class="text-white">Payments</h5>
        </a>
      </div>
      <div class="col-6 col-lg-4" style="--delay: 0.5s">
        <a class="card-slab p-4 d-block h-100" href="${pageContext.request.contextPath}/feedback/list">
          <h5 class="text-white">Feedback</h5>
        </a>
      </div>
    </div>

    <!-- ADMIN PANEL -->
    <c:if test="<%= admin %>">
      <h4 class="text-warning mb-3">Admin Panel</h4>
      <div class="row g-4">
        <div class="col-md-4" style="--delay: 0.1s">
          <a class="card-slab p-4 d-block h-100" href="admin/slots">
            <h5 class="text-white">Parking Slots</h5>
          </a>
        </div>
        <div class="col-md-4" style="--delay: 0.2s">
          <a class="card-slab p-4 d-block h-100" href="admin/users">
            <h5 class="text-white">Users</h5>
          </a>
        </div>
        <div class="col-md-4" style="--delay: 0.3s">
          <a class="card-slab p-4 d-block h-100" href="admin/bookings">
            <h5 class="text-white">Bookings</h5>
          </a>
        </div>
        <div class="col-md-4" style="--delay: 0.4s">
          <a class="card-slab p-4 d-block h-100" href="admin/payments">
            <h5 class="text-white">Payments</h5>
          </a>
        </div>
        <div class="col-md-4" style="--delay: 0.5s">
            <a
              class="card-slab p-4 d-block h-100"
              href="${pageContext.request.contextPath}/admin/reports"
            >
              <h5 class="text-white">Reports</h5>
            </a>
          </div>
      </div>
    </c:if>
  </div><!-- /container -->

  <!-- SCRIPTS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
