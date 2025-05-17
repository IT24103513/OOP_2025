<%@ page import="org.parking.models.User" %>
<%
    User usr = (User) session.getAttribute("user");
%>
<nav class="navbar navbar-expand-lg mb-4">
  <div class="container-fluid">

    <span class="navbar-brand brand text-white">Parking System</span>

    <!-- ====== NAV ITEMS ====== -->
    <ul class="navbar-nav me-auto mb-2 mb-lg-0">

      <!-- Home always -->
      <li class="nav-item">
        <a class="nav-link text-white" href="${pageContext.request.contextPath}/dashboard">Home</a>
      </li>

      <% if (usr != null && usr.isAdmin()) { %>
        <!-- Admin links -->
        <li class="nav-item">
          <a class="nav-link text-white"
             href="${pageContext.request.contextPath}/admin/slots">Slot Management</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-white"
             href="${pageContext.request.contextPath}/admin/users">User Management</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-white"
             href="${pageContext.request.contextPath}/admin/reports">Reports</a>
        </li>
      <% } else { %>
        <!-- Regular-user links -->
        <li class="nav-item">
          <a class="nav-link text-white"
             href="${pageContext.request.contextPath}/vehicle/list">My Vehicles</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-white"
             href="${pageContext.request.contextPath}/bookings">Bookings</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-white"
             href="${pageContext.request.contextPath}/payments">Payments</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-white"
             href="${pageContext.request.contextPath}/feedback/list">Feedback</a>
        </li>
      <% } %>

      <!-- Profile always -->
      <li class="nav-item">
        <a class="nav-link text-white"
           href="${pageContext.request.contextPath}/profile">Profile</a>
      </li>
    </ul>
    <!-- ====== /NAV ITEMS ====== -->

    <!-- user-dropdown (unchanged) -->
    <div class="dropdown ms-auto">
      <a class="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
         href="#" id="userMenu" data-bs-toggle="dropdown" aria-expanded="false">
        <span class="rounded-circle bg-primary d-inline-block text-center fw-bold me-2"
              style="width:36px;height:36px;line-height:36px;">
          <%= (usr!=null? usr.getUsername().substring(0,1).toUpperCase() : "?") %>
        </span>
        <span class="d-none d-lg-inline">
          Welcome <strong><%= (usr!=null? usr.getUsername() : "Guest") %></strong>
        </span>
      </a>
      <ul class="dropdown-menu dropdown-menu-end dropdown-menu-dark" aria-labelledby="userMenu">
        <li>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/profile">
            Profile
          </a>
        </li>
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
