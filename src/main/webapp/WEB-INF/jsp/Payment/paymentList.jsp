<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    org.parking.models.User user =
        (org.parking.models.User) session.getAttribute("user");
    boolean admin = (user instanceof org.parking.models.AdminUser);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Payments</title>

    <!-- 1) Bootstrap FIRST -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">

    <!-- 2) header overrides -->
    <link
      href="${pageContext.request.contextPath}/assets/css/header.css?v=1"
      rel="stylesheet">

    <!-- 3) blurred-bg + frosted-glass + slide-up -->
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

    <!-- My Payments slab -->
    <div class="card-slab p-4 slide-up" style="--delay: 0.1s">
      <h4 class="text-white mb-3">My Payments</h4>
      <div class="table-responsive">
        <table class="table table-dark table-striped table-bordered mb-0">
          <thead>
            <tr>
              <th>ID</th>
              <th>Booking</th>
              <th>Amount ₹</th>
              <th>Method</th>
              <th>Status</th>
              <th>Time</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${payments}" var="p">
              <tr>
                <td>${p.id}</td>
                <td>${p.bookingId}</td>
                <td>${p.amount}</td>
                <td>${p.method}</td>
                <td>${p.status}</td>
                <td>${p.paidAt}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Admin Manage All Payments slab -->
    <c:if test="<%= admin %>">
      <div class="mt-4 card-slab p-4 slide-up" style="--delay: 0.2s">
        <a href="admin/payments" class="text-decoration-none">
          <h5 class="text-warning mb-0">▶ Manage All Payments</h5>
        </a>
      </div>
    </c:if>

  </div><!-- /container -->

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
