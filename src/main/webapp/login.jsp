<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Parking Mee | Login</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/auth.css" rel="stylesheet">
</head>
<body>

<div class="card-glass p-4">
    <h3 class="brand text-center mb-4">ParkMe</h3>
    <h5 class="text-center mb-3 text-white">Sign in</h5>

    <!-- Alerts ----------------------------------------------------------->
    <c:choose>
        <c:when test="${param.msg=='registered'}">
            <div class="alert alert-success py-2">Registration successful – please log in.</div>
        </c:when>
        <c:when test="${param.error=='invalid'}">
            <div class="alert alert-danger py-2">Invalid username or password.</div>
        </c:when>
        <c:when test="${param.error=='logout'}">
            <div class="alert alert-success py-2">You have been logged out.</div>
        </c:when>
    </c:choose>

    <!-- Form ------------------------------------------------------------->
    <form action="login" method="post" class="needs-validation" novalidate>
        <div class="mb-3">
            <label class="form-label text-white">Username</label>
            <input name="username" class="form-control" required>
        </div>

        <div class="mb-4">
            <label class="form-label text-white">Password</label>
            <input type="password" name="password" class="form-control" required>
        </div>

        <button class="btn btn-neon w-100" type="submit">Login</button>
    </form>

    <p class="text-center mt-3 footer">
        Don’t have an account?
        <a href="register.jsp">Register now</a>
    </p>
</div>

<script src="${pageContext.request.contextPath}/assets/js/auth.js"></script>
</body>
</html>
