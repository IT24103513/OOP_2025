<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    // current user & admin check
    org.parking.models.User user =
        (org.parking.models.User) session.getAttribute("user");
    boolean admin = (user instanceof org.parking.models.AdminUser);
%>
<!DOCTYPE html>
<html>
<head>
  <title>Parking Slots</title>

  <!-- 1) Bootstrap FIRST -->
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
  >

  <!-- 2) header overrides -->
  <link
    href="${pageContext.request.contextPath}/assets/css/header.css?v=1"
    rel="stylesheet"
  >

  <!-- 3) blurred-bg + frosted-glass + slide-up -->
  <link
    href="${pageContext.request.contextPath}/assets/css/dashboard.css?v=1"
    rel="stylesheet"
  >
</head>
<body>

  <!-- full-screen blurred bg -->
  <div class="bg-blur"></div>

  <!-- NAVBAR -->
    <!-- NAVBAR -->
    <%@ include file="/WEB-INF/navbar.jsp" %>

  <!-- CONTENT -->
  <div class="container">

    <!-- Define Slot button -->
    <div class="slide-up mb-3" style="--delay: 0.1s">
      <a href="slot/add" class="btn btn-success">
        + Define Slot
      </a>
    </div>

    <!-- Slots list slab -->
    <div class="card-slab p-4 slide-up" style="--delay: 0.2s">
      <h4 class="text-white mb-3">Parking Slots</h4>
      <div class="table-responsive">
        <table class="table table-dark table-striped table-bordered mb-0">
          <thead>
            <tr>
              <th>ID</th>
              <th>Type</th>
              <th>Status</th>
              <th>Hourly ₹</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${slots}" var="s">
              <tr>
                <td>${s.number}</td>
                <td>${s.type}</td>
                <td>${s.status}</td>
                <td>${s.chargePerHour}</td>
                <td>
                  <form
                    action="slot/status"
                    method="post"
                    class="d-inline"
                  >
                    <input type="hidden" name="number" value="${s.number}" />
                    <input
                      type="hidden"
                      name="occupied"
                      value="${s.status=='AVAILABLE'}"
                    />
                    <button class="btn btn-sm btn-warning">
                      ${s.status=='AVAILABLE'
                        ? 'Mark Occupied'
                        : 'Mark Vacant'}
                    </button>
                  </form>
                  <form
                    action="slot/delete"
                    method="post"
                    class="d-inline ms-1"
                    onsubmit="return confirm('Delete slot?')"
                  >
                    <input type="hidden" name="number" value="${s.number}" />
                    <button class="btn btn-sm btn-danger">🗑</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <!-- (Optional) Admin link to manage slots -->
    <c:if test="<%= admin %>">
      <div class="mt-4 slide-up" style="--delay: 0.3s">
        <a href="admin/slots" class="card-slab p-4 text-decoration-none">
          <h5 class="text-warning mb-0">▶ Manage All Slots</h5>
        </a>
      </div>
    </c:if>

  </div><!-- /container -->

  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
  ></script>
</body>
</html>
