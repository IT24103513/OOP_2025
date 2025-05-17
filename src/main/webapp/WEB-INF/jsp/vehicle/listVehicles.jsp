<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    // optional: pull user from session (navbar.jsp also does this)
    org.parking.models.User user =
        (org.parking.models.User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Vehicles</title>

  <!-- 1) Bootstrap CSS -->
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
  >

  <!-- 2) Your header overrides -->
  <link
    href="${pageContext.request.contextPath}/assets/css/header.css?v=1"
    rel="stylesheet"
  >

  <!-- 3) Vehicles page CSS (contains the new blur + glass + slide-up + neon styles) -->
  <link
    href="${pageContext.request.contextPath}/assets/css/vehicles.css?v=2"
    rel="stylesheet"
  >

</head>
<body>

  <!-- full-screen blurred background -->
  <div class="bg-blur"></div>

  <!-- NAVBAR (reads session user internally) -->
  <%@ include file="/WEB-INF/navbar.jsp" %>

  <!-- MAIN CONTENT -->
  <div class="container py-5">
    <div
      class="card-slab p-4"
      style="--delay: 0.1s"
    >
      <h4 class="text-white mb-3">My Vehicles</h4>

      <c:choose>
        <c:when test="${empty vehicles}">
          <p class="text-secondary">
            No vehicles yet.
            <a href="${pageContext.request.contextPath}/vehicle/add">Add one</a>.
          </p>
        </c:when>
        <c:otherwise>
          <div class="table-responsive">
            <table class="table table-dark table-hover align-middle mb-0">
              <thead>
                <tr>
                  <th>Plate</th>
                  <th>Type</th>
                  <th>Color</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="v" items="${vehicles}">
                  <tr>
                    <td>${v.plateNumber}</td>
                    <td>${v.vehicleType()}</td>
                    <td>${v.color}</td>
                    <td>
                      <a
                        class="btn btn-sm btn-outline-light"
                        href="edit?plate=${v.plateNumber}"
                      >Edit</a>
                      <form
                        action="delete"
                        method="post"
                        class="d-inline ms-1"
                        onsubmit="return confirm('Delete vehicle?');"
                      >
                        <input
                          type="hidden"
                          name="plate"
                          value="${v.plateNumber}"
                        />
                        <button class="btn btn-sm btn-danger">Delete</button>
                      </form>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:otherwise>
      </c:choose>

      <a
        href="${pageContext.request.contextPath}/vehicle/add"
        class="btn btn-neon mt-4"
        style="--delay: 0.2s"
      >
        Add another vehicle
      </a>
    </div>
  </div>

  <!-- Bootstrap JS bundle -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
