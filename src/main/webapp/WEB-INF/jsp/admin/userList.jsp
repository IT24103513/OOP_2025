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
  <title>User Administration</title>

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

  <style>
  /* darken the backdrop */
  .modal-backdrop.show {
    background-color: rgba(0,0,0,0.75) !important;
  }
  /* frosted‐glass modal window */
  .modal-content {
    background: rgba(22,27,34,0.9) !important;
    border: 1px solid rgba(255,255,255,0.2) !important;
    color: #c9d1d9 !important;
    backdrop-filter: blur(8px);
    -webkit-backdrop-filter: blur(8px);
  }
  /* header/title/text */
  .modal-header,
  .modal-header .modal-title,
  .modal-body,
  .modal-footer {
    color: #c9d1d9 !important;
  }
  .modal-header .btn-close {
    filter: invert(1) !important;
  }
  /* form controls in modal */
  .modal-body .form-control,
  .modal-body .form-select {
    background: #0d1117 !important;
    border-color: #30363d !important;
    color: #c9d1d9 !important;
  }
  .modal-body .form-control::placeholder {
    color: rgba(201,209,217,0.6) !important;
  }
  .modal-body .form-check-input {
    background-color: #161b22 !important;
    border-color: #30363d !important;
  }
  .modal-body .form-check-input:checked {
    background-color: #198754 !important;
    border-color: #198754 !important;
  }
  /* buttons in the footer */
  .modal-footer .btn-primary {
    background-color: #0d6efd !important;
    border-color: #0d6efd !important;
  }
  .modal-footer .btn-secondary {
    background-color: #6c757d !important;
    border-color: #6c757d !important;
  }
  </style>



</head>
<body>

  <!-- full-screen blurred bg -->
  <div class="bg-blur"></div>

  <!-- NAVBAR (same as before) -->
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

    <!-- User Administration slab -->
    <div class="card-slab p-4 slide-up" style="--delay: 0.1s">
      <h4 class="text-white mb-3">User Administration</h4>
      <div class="table-responsive">
        <table class="table table-dark table-striped table-bordered mb-0">
          <thead>
            <tr>
              <th>Username</th>
              <th>Email</th>
              <th>Role</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${users}" var="u">
              <tr>
                <td>${u.username}</td>
                <td>${u.email}</td>
                <td><c:out value="${u.getClass().simpleName}"/></td>
                <td>
                  <!-- Existing Perms button for admins -->
                  <c:if test="${u.admin}">
                    <form action="user/perm" method="post" class="d-inline">
                      <input type="hidden" name="username" value="${u.username}" />
                      <button type="submit" class="btn btn-sm btn-secondary">Perms</button>
                    </form>
                  </c:if>

                  <!-- NEW: Change Role button -->
                  <button
                    type="button"
                    class="btn btn-sm btn-info btn-change-role ms-1"
                    data-username="${u.username}"
                    data-role="<c:out value='${u.getClass().simpleName}'/>"
                    data-bs-toggle="modal"
                    data-bs-target="#roleModal"
                  >Change Role</button>

                  <!-- Delete user -->
                  <form action="user/delete" method="post" class="d-inline ms-1"
                        onsubmit="return confirm('Delete user?')">
                    <input type="hidden" name="username" value="${u.username}" />
                    <button
                      type="button"
                      class="btn btn-sm btn-danger btn-delete-user"
                      data-username="${u.username}"
                      data-bs-toggle="modal"
                      data-bs-target="#deleteUserModal"
                    >🗑</button>

                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

  </div><!-- /container -->

  <!-- Change Role Modal -->
  <div class="modal fade" id="roleModal" tabindex="-1" aria-labelledby="roleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <form action="user/changeRole" method="post" class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="roleModalLabel">Change User Role</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="username" id="role-username" />
          <p>Current role: <strong id="current-role"></strong></p>
          <div class="form-check">
            <input
              class="form-check-input"
              type="radio"
              name="newRole"
              id="roleAdmin"
              value="AdminUser"
              required
            >
            <label class="form-check-label text-white" for="roleAdmin">
              Admin
            </label>
          </div>
          <div class="form-check">
            <input
              class="form-check-input"
              type="radio"
              name="newRole"
              id="roleVisitor"
              value="VisitorUser"
            >
            <label class="form-check-label text-white" for="roleVisitor">
              Visitor
            </label>
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >Cancel</button>
          <button type="submit" class="btn btn-primary">Change Role</button>
        </div>
      </form>
    </div>
  </div>



  <!-- Delete Confirmation Modal -->
  <div class="modal fade" id="deleteUserModal" tabindex="-1" aria-labelledby="deleteUserLabel" aria-hidden="true">
    <div class="modal-dialog">
      <form id="deleteUserForm" method="post" action="user/delete" class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title text-white" id="deleteUserLabel">Confirm Delete</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body text-white">
          <p>Are you sure you want to delete user <strong id="delete-username"></strong>?</p>
          <input type="hidden" name="username" id="delete-user-input">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-danger">Delete</button>
        </div>
      </form>
    </div>
  </div>



  <!-- Bootstrap JS & initialization script -->
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
  ></script>
  <script>
    document.querySelectorAll('.btn-change-role').forEach(btn => {
      btn.addEventListener('click', () => {
        // populate modal fields
        document.getElementById('role-username').value = btn.dataset.username;
        document.getElementById('current-role').textContent = btn.dataset.role;
        // pre-check the opposite role
        document.getElementById('roleAdmin').checked = btn.dataset.role !== 'AdminUser';
        document.getElementById('roleVisitor').checked = btn.dataset.role === 'AdminUser';
      });
    });
  </script>
</body>
</html>
