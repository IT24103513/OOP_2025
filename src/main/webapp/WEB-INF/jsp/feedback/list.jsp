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
  <title>My Feedbacks</title>

  <!-- 1) Bootstrap -->
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
  >

  <!-- 2) Header overrides -->
  <link
    href="${pageContext.request.contextPath}/assets/css/header.css?v=1"
    rel="stylesheet"
  >

  <!-- 3) Blurred-bg + frosted-glass + slide-up -->
  <link
    href="${pageContext.request.contextPath}/assets/css/dashboard.css?v=1"
    rel="stylesheet"
  >
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

    <!-- My Feedbacks slab -->
    <div class="card-slab p-4 slide-up" style="--delay: 0.1s">
      <h4 class="text-white mb-3">My Feedbacks</h4>

      <!-- Add Feedback button -->
      <button
        type="button"
        class="btn btn-success mb-3"
        data-bs-toggle="modal"
        data-bs-target="#addFeedbackModal"
      >+ New Feedback</button>

      <!-- feedback table -->
      <div class="table-responsive">
        <table class="table table-dark table-striped table-bordered mb-0">
          <thead>
            <tr>
              <th>Subject</th>
              <th>Status</th>
              <th>Created</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${entries}" var="f">
              <tr>
                <td>${f.displayHeader()}</td>
                <td>${f.status}</td>
                <td>${f.createdAt}</td>
                <td>
                  <button
                    type="button"
                    class="btn btn-sm btn-primary btn-edit"
                    data-id="${f.id}"
                    data-subject="${f.subject}"
                    data-content="${f.content}"
                    data-status="${f.status}"
                    data-bs-toggle="modal"
                    data-bs-target="#editFeedbackModal"
                  >Edit</button>
                  <form
                    action="<c:url value='/feedback/delete'/>"
                    method="post"
                    class="d-inline ms-1"
                    onsubmit="return confirm('Delete feedback?')"
                  >
                    <input type="hidden" name="id" value="${f.id}" />
                    <button class="btn btn-sm btn-danger">Delete</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Admin link -->
    <c:if test="<%= admin %>">
      <div class="mt-4 slide-up" style="--delay: 0.2s">
        <a href="admin/feedback/list" class="card-slab p-4 text-decoration-none">
          <h5 class="text-warning mb-0">▶ Manage All Feedback</h5>
        </a>
      </div>
    </c:if>

  </div><!-- /container -->

  <!-- Modals -->
  <div class="modal fade" id="addFeedbackModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <form
        method="post"
        action="<c:url value='/feedback/add'/>"
        class="modal-content"
      >
        <div class="modal-header">
          <h5 class="modal-title">Submit New Feedback</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="feedbackSubject" class="form-label text-white">Subject</label>
            <input
              type="text"
              class="form-control"
              id="feedbackSubject"
              name="subject"
              required
            />
          </div>
          <div class="mb-3">
            <label for="feedbackContent" class="form-label text-white">Content</label>
            <textarea
              class="form-control"
              id="feedbackContent"
              name="content"
              rows="4"
              required
            ></textarea>
          </div>
          <div class="form-check">
            <input
              class="form-check-input"
              type="checkbox"
              id="anonymousCheck"
              name="anonymous"
            />
            <label class="form-check-label text-white" for="anonymousCheck">
              Submit anonymously
            </label>
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >Cancel</button>
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </form>
    </div>
  </div>

  <div class="modal fade" id="editFeedbackModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <form
        method="post"
        action="<c:url value='/feedback/edit'/>"
        class="modal-content"
      >
        <div class="modal-header">
          <h5 class="modal-title">Edit Feedback</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" id="edit-id" />
          <div class="mb-3">
            <label for="edit-subject" class="form-label text-white">Subject</label>
            <input
              type="text"
              class="form-control"
              id="edit-subject"
              name="subject"
              required
            />
          </div>
          <div class="mb-3">
            <label for="edit-content" class="form-label text-white">Content</label>
            <textarea
              class="form-control"
              id="edit-content"
              name="content"
              rows="4"
              required
            ></textarea>
          </div>
          <c:if test="${sessionScope.user.admin}">
            <div class="mb-3">
              <label for="edit-status" class="form-label text-white">Status</label>
              <select class="form-select" id="edit-status" name="status">
                <option>OPEN</option>
                <option>IN_PROGRESS</option>
                <option>CLOSED</option>
              </select>
            </div>
          </c:if>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            Cancel
          </button>
          <button type="submit" class="btn btn-primary">Save</button>
        </div>
      </form>
    </div>
  </div>

  <!-- Bootstrap JS + edit-handler -->
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
  ></script>
  <script>
    document.querySelectorAll('.btn-edit').forEach(btn => {
      btn.addEventListener('click', () => {
        document.getElementById('edit-id').value = btn.dataset.id;
        document.getElementById('edit-subject').value = btn.dataset.subject;
        document.getElementById('edit-content').value = btn.dataset.content;
        if (btn.dataset.status) {
          document.getElementById('edit-status').value = btn.dataset.status;
        }
      });
    });
  </script>
</body>
</html>
