<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<%@ page import="
    org.parking.services.ParkingSlotService,
    org.parking.models.Bookings.Booking,
    org.parking.models.Bookings.BookingStatus,
    java.time.LocalDateTime"
 %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    org.parking.models.User user =
        (org.parking.models.User) session.getAttribute("user");
    boolean admin = (user instanceof org.parking.models.AdminUser);

    ParkingSlotService slotSvc = new ParkingSlotService();
        LocalDateTime now = LocalDateTime.now();

%>
<!DOCTYPE html>
<html>
<head>
    <title>Bookings</title>

    <!-- Bootstrap CSS -->

    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">

    <!-- Your custom CSS -->
    <link href="${pageContext.request.contextPath}/assets/css/header.css?v=1" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/bookingsFromDashboard.css?v=1" rel="stylesheet">

</head>
<body>

  <!-- full-screen blurred bg -->
  <div class="bg-blur"></div>

  <!-- NAVBAR -->
  <%@ include file="/WEB-INF/navbar.jsp" %>


  <!-- CONTENT -->
  <div class="container">

    <!-- + New Booking button -->
    <div class="mb-4 slide-up" style="--delay:0.1s">
      <a href="booking/add" class="btn btn-success">+ New Booking</a>
    </div>

    <!-- bookings table -->
    <div class="card-slab p-4 slide-up" style="--delay:0.2s">

      <h4 class="text-white mb-3">My Bookings</h4>
      <div class="table-responsive">
        <table class="table table-dark table-striped table-bordered mb-0">
          <thead>
            <tr>
              <th>ID</th><th>Vehicle</th><th>Slot</th><th>Type</th>
              <th>Start</th><th>End</th><th>Fee Rs.</th><th>Status</th><th>Actions</th>
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

                <%
                                  Booking bBean = (Booking) pageContext.getAttribute("b");
                                  double feeToShow;
                                  if (bBean.getStatus() == BookingStatus.ACTIVE) {
                                      double rate = slotSvc.listAll().stream()
                                          .filter(s->s.getNumber()==bBean.getSlotNumber())
                                          .findFirst()
                                          .map(s->s.getChargePerHour())
                                          .orElse(0.0);
                                      feeToShow = bBean.calculateFee(rate, bBean.getStartTime(), now);
                                  } else {
                                      feeToShow = bBean.getFee();
                                  }
                                %>
                                <td><%= String.format("%.2f", feeToShow) %></td>



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

    <!-- ADMIN LINK -->
    <c:if test="<%= admin %>">
      <div class="mt-5 slide-up" style="--delay:0.3s">

        <a href="admin/bookings" class="card-slab p-4 d-block text-decoration-none">
          <h5 class="text-warning mb-0">▶ Manage All Bookings</h5>
        </a>
      </div>
    </c:if>

  </div><!-- /container -->


  <!-- PAYMENT MODAL -->
  <div class="modal fade" id="paymentModal" tabindex="-1">
    <div class="modal-dialog">
      <form
        id="paymentForm"
        method="post"
        action="booking/complete"
        class="modal-content needs-validation"
        novalidate
      >

        <div class="modal-header">
          <h5 class="modal-title">Choose Payment Method</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" id="modalBookingId">
          <p>Amount due: Rs.<span id="modalFee"></span></p>

          <!-- payment mode -->

          <div class="mb-3">
            <select name="mode" id="paymentMode" class="form-select" required>
              <option value="">-- select --</option>
              <option value="CASH">Cash at gate</option>
              <option value="CARD">Card</option>
            </select>
            <div class="invalid-feedback">Please choose a payment method.</div>
          </div>

          <!-- card details group -->
          <div id="cardDetails" class="d-none">
            <!-- Name on Card -->
            <div class="mb-3">
              <label for="cardName" class="form-label">Name on Card</label>
              <input
                type="text"
                class="form-control"
                id="cardName"
                name="cardName"
                placeholder="Exactly as on your card"
                required
              >
              <div class="invalid-feedback">Name on card is required.</div>
            </div>

            <!-- Card Number + brand -->
            <div class="mb-3 position-relative">
              <label for="cardNumber" class="form-label">Card Number</label>
              <input
                type="text"
                class="form-control pe-5"
                id="cardNumber"
                name="cardNumber"
                placeholder="XXXX XXXX XXXX XXXX"
                maxlength="19"
                required
              >
              <span
                id="cardBrand"
                class="position-absolute top-50 end-0 translate-middle-y me-3"
              ></span>
              <div class="invalid-feedback">Please enter a valid card number.</div>
            </div>

            <div class="row">
              <!-- Expiry -->
              <div class="col-6 mb-3">
                <label for="cardExpiry" class="form-label">Expiry (MM/YY)</label>
                <input
                  type="text"
                  class="form-control"
                  id="cardExpiry"
                  name="cardExpiry"
                  placeholder="MM/YY"
                  pattern="^(0[1-9]|1[0-2])\/\d{2}$"
                  required
                >
                <div class="invalid-feedback">Use MM/YY format (e.g. 04/25).</div>
              </div>
              <!-- CVC -->
              <div class="col-6 mb-3">
                <label for="cardCvc" class="form-label">CVC</label>
                <input
                  type="text"
                  class="form-control"
                  id="cardCvc"
                  name="cardCvc"
                  placeholder="123"
                  pattern="^\d{3,4}$"
                  required
                >
                <div class="invalid-feedback">3 or 4 digit CVC required.</div>
              </div>
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            Cancel
          </button>

          <button type="submit" class="btn btn-primary">Proceed</button>
        </div>
      </form>
    </div>
  </div>


  <!-- Bootstrap JS bundle -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Payment modal logic, brand-detect & validation -->
  <script>
    document.addEventListener('DOMContentLoaded', () => {
      const exitBtns   = document.querySelectorAll('.exit-btn');
      const modalEl    = document.getElementById('paymentModal');
      const modal      = new bootstrap.Modal(modalEl);
      const feeSpan    = document.getElementById('modalFee');
      const bookingId  = document.getElementById('modalBookingId');
      const modeSelect = document.getElementById('paymentMode');
      const cardBlock  = document.getElementById('cardDetails');
      const form       = document.getElementById('paymentForm');
      const numberIn   = document.getElementById('cardNumber');
      const brandSpan  = document.getElementById('cardBrand');

      // 1) show modal & reset
      exitBtns.forEach(btn => {
        btn.addEventListener('click', () => {
          bookingId.value = btn.dataset.id;
          feeSpan.textContent = btn.dataset.fee;
          modeSelect.value = '';
          cardBlock.classList.add('d-none');
          form.classList.remove('was-validated');
          ['cardName','cardNumber','cardExpiry','cardCvc']
            .forEach(id => document.getElementById(id).value = '');
          brandSpan.textContent = '';
          modal.show();
        });
      });

      // 2) toggle card fields
      modeSelect.addEventListener('change', e => {
        if (e.target.value === 'CARD') {
          cardBlock.classList.remove('d-none');
        } else {
          cardBlock.classList.add('d-none');
        }
      });

      // 3) brand detection
      numberIn.addEventListener('input', e => {
        const v = e.target.value.replace(/\D/g,'');
        brandSpan.textContent =
          /^4/.test(v) ? '💳 Visa' :
          /^(5[1-5]|2[2-7])/.test(v) ? '💳 MasterCard' :
          '';
      });

      // 4) bootstrap-style validation + Luhn
      form.addEventListener('submit', e => {
        form.classList.add('was-validated');

        if (modeSelect.value !== 'CARD') {
          if (!modeSelect.checkValidity()) e.preventDefault();
          return;
        }

        // Luhn check
        const digits = numberIn.value.replace(/\D/g,'');
        let sum = 0, flip = false;
        for (let i = digits.length - 1; i >= 0; i--) {
          let d = +digits[i];
          if (flip) { d *= 2; if (d>9) d-=9; }
          sum += d;
          flip = !flip;
        }
        if (sum % 10 !== 0) {
          numberIn.setCustomValidity('fail');
        } else {
          numberIn.setCustomValidity('');
        }

        if (!form.checkValidity()) {
          e.preventDefault();
          e.stopPropagation();
        }
      });
    });
  </script>

</body>
</html>
