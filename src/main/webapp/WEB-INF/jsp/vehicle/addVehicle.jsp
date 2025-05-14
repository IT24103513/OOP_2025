<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Add Vehicle</title>

    <!-- 1) Bootstrap FIRST -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    >

    <!-- 2) Then your custom Add-Vehicle CSS -->
    <link
      href="${pageContext.request.contextPath}/assets/css/vehicles.css"
      rel="stylesheet"
    >
  </head>
  <body class="p-4">
    <div class="card-slab p-4 mx-auto" style="max-width:500px">
      <h4 class="text-white mb-3">Register Vehicle</h4>

      <c:if test="${param.error=='dup'}">
        <div class="alert alert-danger py-2">Plate already registered.</div>
      </c:if>

      <form action="add" method="post" class="needs-validation" novalidate>
        <div class="mb-3">
          <label class="form-label text-white">Plate Number</label>
          <input name="plate" class="form-control" required>
        </div>
        <div class="mb-3">
          <label class="form-label text-white">Color</label>
          <input name="color" class="form-control" required>
        </div>
        <div class="mb-4">
          <label class="form-label text-white">Type</label>
          <select name="type" class="form-select">
            <option>Car</option>
            <option>Bike</option>
            <option>Truck</option>
          </select>
        </div>
        <button class="btn btn-neon w-100">Add Vehicle</button>
      </form>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/auth.js"></script>
  </body>
</html>
