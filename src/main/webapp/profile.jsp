<%@page contentType="text/html;charset=UTF-8" language="java" session="true"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%
    org.parking.models.User user =
        (org.parking.models.User) session.getAttribute("user");
    boolean admin = (user instanceof org.parking.models.AdminUser);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Parking System | Profile</title>

    <!-- Google Font & Bootstrap -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Local style: note the “?v=1” to bust cache -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/profile.css?v=1">

    <style>
      /* just the avatar sizing tweak */
      .profile-placeholder {
        width: 80px;
        height: 80px;
        line-height: 80px;
        font-size: 2rem;
      }
    </style>
</head>
<body>

  <!-- full-screen blurred BG -->
  <div class="bg-blur"></div>

  <!-- NAV ------------------------------------------------------------------->
  <%@ include file="/WEB-INF/navbar.jsp" %>

  <div class="container">
    <div class="row">
      <!-- LEFT COLUMN: Profile‐pic -->
      <div class="col-md-3">
        <div class="card-slab p-4 mb-4 text-center" data-aos>
          <span class="rounded-circle bg-primary d-inline-block text-white fw-bold profile-placeholder">
            <%= user.getUsername().substring(0,1).toUpperCase() %>
          </span>
          <h5 class="mt-3 mb-0"><%= user.getUsername() %></h5>
        </div>
      </div>

      <!-- RIGHT COLUMN -->
      <div class="col-md-9">
        <div class="d-flex flex-column align-items-end">

          <!-- PROFILE CARD -->
          <div class="card-slab p-4 mb-4" data-aos>
            <h3 class="mb-3">Profile</h3>
            <dl class="row mb-0">
              <dt class="col-sm-3">Username</dt>
              <dd class="col-sm-9"><%= user.getUsername() %></dd>

              <dt class="col-sm-3">E-mail</dt>
              <dd class="col-sm-9"><%= user.getEmail() %></dd>

              <dt class="col-sm-3">Member&nbsp;since</dt>
              <dd class="col-sm-9">
                <fmt:formatDate value="<%= java.sql.Timestamp.valueOf(user.getCreatedAt()) %>"
                                pattern="yyyy-MM-dd HH:mm"/>
              </dd>

              <dt class="col-sm-3">Role</dt>
              <dd class="col-sm-9">
                <c:choose>
                  <c:when test="<%= admin %>">Administrator</c:when>
                  <c:otherwise>Regular&nbsp;user</c:otherwise>
                </c:choose>
              </dd>
            </dl>

            <!-- edit email form -->
            <form class="row gy-2 mt-4" action="profile/edit" method="post">
              <div class="col-md-8">
                <input name="email" type="email" class="form-control"
                       placeholder="Update email address" required>
              </div>
              <div class="col-md-4 d-grid">
                <button class="btn btn-neon">Update&nbsp;Email</button>
              </div>
            </form>

            <c:if test="${param.msg=='updated'}">
              <div class="alert alert-success mt-3 py-2">Profile updated!</div>
            </c:if>
          </div>

          <!-- ADMIN PANEL (if admin) -->
          <c:if test="<%= admin %>">
            <div class="card-slab p-4 mb-4" data-aos>
              <h4 class="mb-3 text-warning">Admin&nbsp;Tools</h4>
              <p class="text-secondary mb-3">(Visible to administrators only.)</p>
              <div class="d-flex flex-wrap gap-3">
                <a href="#" class="btn btn-outline-light flex-fill">User&nbsp;Mgmt</a>
                <a href="#" class="btn btn-outline-light flex-fill">Parking&nbsp;Slots</a>
                <a href="#" class="btn btn-outline-light flex-fill">Reports</a>
              </div>
            </div>
          </c:if>

          <!-- DANGER ZONE -->
          <div class="card-slab p-4 mb-5" data-aos>
            <h4 class="mb-3 text-danger">Danger Zone</h4>
            <form action="profile/delete" method="post"
                  onsubmit="return confirm('Delete your account permanently?');">
              <button class="btn btn-danger w-100">Delete&nbsp;Account</button>
            </form>
          </div>

        </div>
      </div>
    </div>
  </div><!-- /container -->

  <!-- SCRIPTS -->
  <script src="${pageContext.request.contextPath}/assets/js/profile.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
