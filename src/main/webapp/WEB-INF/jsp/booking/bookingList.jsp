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
    <title>Bookings</title>

    <!-- Bootstrap -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">

    <!-- 1) header overrides first -->
    <link
      href="${pageContext.request.contextPath}/assets/css/header.css?v=1"
      rel="stylesheet">

    <!-- 2) blurred-bg + frosted-glass + slide-up -->
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

    <!-- + New Booking button -->
    <div class="mb-4" style="--delay: 0.1s">
      <a href="booking/add" class="btn btn-success slide-up" style="--delay:0.1s">
        + New Booking
      </a>
    </div>

    <!-- bookings table in a frosted-glass slab -->
    <div class="card-slab p-4 slide-up" style="--delay: 0.2s">
      <h4 class="text-white mb-3">My Bookings</h4>
      <div class="table-responsive">
        <table class="table table-dark table-striped table-bordered mb-0">
          <thead>
            <tr>
              <th>ID</th>
              <th>Vehicle</th>
              <th>Slot</th>
              <th>Type</th>
              <th>Start</th>
              <th>End</th>
              <th>Fee Rs.</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${bookings}" var="b">
              <tr>
                <td>${b.id}</td>
                <td>${b.vehiclePlate}</td>
                <td>${b.slotNumber}</td>
                <td>${b.type}</td>
                <td>${b.startTime}</td>
                <td>${b.endTime}</td>
                <td>${b.fee}</td>
                <td>${b.status}</td>
                <td>
                  <c:if test="${b.status=='ACTIVE'}">
                    <button
                      class="btn btn-warning exit-btn"
                      data-id="${b.id}"
                      data-fee="${b.fee}"
                    >Exit</button>
                  </c:if>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <!-- ADMIN PANEL LINK TO BOOKINGS -->
    <c:if test="<%= admin %>">
      <div class="mt-5 slide-up" style="--delay: 0.3s">
        <a href="admin/bookings" class="card-slab p-4 d-block text-decoration-none">
          <h5 class="text-warning mb-0">▶ Manage All Bookings</h5>
        </a>
      </div>
    </c:if>

  </div><!-- /container -->

  <!-- SCRIPTS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    // show payment modal
    document.querySelectorAll('.exit-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        const modal = new bootstrap.Modal(document.getElementById('paymentModal'));
        document.getElementById('modalBookingId').value = btn.dataset.id;
        document.getElementById('modalFee').textContent = btn.dataset.fee;
        document.getElementById('paymentMode').value = '';
        document.getElementById('cardInputGroup').classList.add('d-none');
        document.getElementById('cardNumber').value = '';
        modal.show();
      });
    });
    // toggle card input
    document.getElementById('paymentMode')?.addEventListener('change', e => {
      const cg = document.getElementById('cardInputGroup');
      if (e.target.value === 'CARD') {
        cg.classList.remove('d-none');
        document.getElementById('cardNumber').setAttribute('required','required');
      } else {
        cg.classList.add('d-none');
        document.getElementById('cardNumber').removeAttribute('required');
      }
    });
  </script>

  <!-- PAYMENT MODAL (copy from your other page) -->
  <div class="modal fade" id="paymentModal" tabindex="-1">
    <div class="modal-dialog">
      <form method="post" action="booking/complete" class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Choose Payment Method</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" id="modalBookingId">
          <p>Amount due: ₹<span id="modalFee"></span></p>
          <div class="mb-3">
            <select name="mode" id="paymentMode" class="form-select" required>
              <option value="">-- select --</option>
              <option value="CASH">Cash at gate</option>
              <option value="CARD">Card</option>
            </select>
          </div>
          <div class="mb-3 d-none" id="cardInputGroup">
            <label for="cardNumber" class="form-label">Card Number</label>
            <input
              type="text"
              name="cardNumber"
              id="cardNumber"
              class="form-control"
              placeholder="XXXX XXXX XXXX XXXX"
            >
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-primary">Proceed</button>
        </div>
      </form>
    </div>
  </div>

</body>
</html>
