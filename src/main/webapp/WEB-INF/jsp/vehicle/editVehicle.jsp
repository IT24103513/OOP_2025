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
    <title>Edit Vehicle</title>

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
  <nav class="navbar navbar-expand-lg mb-4">
    <div class="container-fluid">
      <span class="navbar-brand brand text-white">Parking System</span>
      <div class="dropdown ms-auto">
        <a class="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
           href="#" id="userMenu" data-bs-toggle="dropdown" aria-expanded="false">
          <span class="rounded-circle bg-primary d-inline-block text-center fw-bold me-2"
                style="width:36px;height:36px;line-height:36px;">
            <%= user.getUsername().substring(0,1).toUpperCase() %>
          </span>
          <span class="d-none d-lg-inline">
            Welcome <strong><%= user.getUsername() %></strong>
          </span>
        </a>
        <ul class="dropdown-menu dropdown-menu-end dropdown-menu-dark" aria-labelledby="userMenu">
          <li><a class="dropdown-item" href="profile">Profile</a></li>
          <li><hr class="dropdown-divider"></li>
          <li>
            <form action="<c:url value='/logout'/>" method="post" class="m-0">
              <button class="dropdown-item">Logout</button>
            </form>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- CONTENT -->
  <div class="container">

    <div class="card-slab p-4 mx-auto slide-up" style="--delay: 0.1s; max-width:500px;">
      <h4 class="text-white mb-3">Edit Vehicle</h4>

      <form action="edit" method="post" class="needs-validation" novalidate>
        <input type="hidden" name="plate" value="${veh.plateNumber}">

        <div class="mb-3">
          <label class="form-label text-white">Plate</label>
          <input class="form-control" value="${veh.plateNumber}" disabled>
        </div>

        <div class="mb-3">
          <label class="form-label text-white">Type</label>
          <input class="form-control" value="${veh.vehicleType()}" disabled>
        </div>

        <div class="mb-4">
          <label class="form-label text-white">Color</label>
          <input
            name="color"
            class="form-control"
            value="${veh.color}"
            required>
        </div>

        <button class="btn btn-neon w-100">Save</button>
      </form>
    </div>

  </div><!-- /container -->

  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
  ></script>
  <script src="${pageContext.request.contextPath}/assets/js/auth.js"></script>
</body>
</html>
