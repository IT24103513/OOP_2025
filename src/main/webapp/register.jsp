<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Parking System | Register</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/auth.css" rel="stylesheet">
</head>
<body>

<div class="card-glass p-4">
    <h3 class="brand text-center mb-4">ParkMe</h3>
    <h5 class="text-center mb-3 text-white">Create an account</h5>

    <!-- Alerts ----------------------------------------------------------->
    <c:choose>
        <c:when test="${param.error=='exists'}">
            <div class="alert alert-danger py-2">Username already exists.</div>
        </c:when>
        <c:when test="${param.error=='missing'}">
            <div class="alert alert-danger py-2">Please fill in every field.</div>
        </c:when>
    </c:choose>

    <!-- Form ------------------------------------------------------------->
    <form action="register" method="post" class="needs-validation" novalidate>
        <div class="mb-3">
            <label class="form-label text-white">Username</label>
            <input name="username" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label text-white">E‑mail</label>
            <input type="email" name="email" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label text-white">Password</label>
            <input type="password" name="password" class="form-control" minlength="4" required>
        </div>

        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" name="admin" id="adminChk">
            <label class="form-check-label text-white" for="adminChk">Register as administrator</label>
        </div>

        <button class="btn btn-neon w-100" type="submit">Register</button>
    </form>

    <p class="text-center mt-3 footer">
        Already have an account?
        <a href="login.jsp">Log in</a>
    </p>
</div>

<script src="${pageContext.request.contextPath}/assets/js/auth.js"></script>
</body>
</html>
