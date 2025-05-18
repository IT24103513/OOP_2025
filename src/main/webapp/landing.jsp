<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>ParkMe | Home</title>

  <!-- Bootstrap 5 -->
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
  />

  <!-- AOS (Animate On Scroll) -->
  <link
    href="https://unpkg.com/aos@2.3.1/dist/aos.css"
    rel="stylesheet"
  />

  <!-- FontAwesome (for feature icons) -->
  <link
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
    rel="stylesheet"
  />

  <!-- Your custom landing CSS -->
  <link
    href="${pageContext.request.contextPath}/assets/css/landing.css"
    rel="stylesheet"
  />
</head>
<body>

  <!-- NAVBAR -->
  <nav class="navbar navbar-expand-lg navbar-light navbar-transparent fixed-top">
    <div class="container">
      <a class="navbar-brand text-white fs-3 fw-bold" href="#">
        🅿️ ParkMe
      </a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navMenu"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

    </div>
  </nav>

  <!-- HERO SECTION -->
  <section class="hero d-flex align-items-center justify-content-center text-center">
    <div class="container text-white" data-aos="fade-up">
      <h1 class="display-3 fw-bold">Welcome to ParkMe</h1>
      <p class="lead mb-4">
        Your smart, secure, real-time parking management solution.
      </p>
      <a href="login.jsp" class="btn btn-outline-light btn-lg me-3">
        <i class="fas fa-sign-in-alt me-2"></i>Login
      </a>
      <a href="register.jsp" class="btn btn-light btn-lg">
        <i class="fas fa-user-plus me-2"></i>Register
      </a>
    </div>
  </section>

  <!-- FEATURES SECTION -->
  <section class="features py-5">
    <div class="container">
      <div class="row g-4">
        <div class="col-md-4" data-aos="fade-up" data-aos-delay="100">
          <div class="feature-card p-4 text-center h-100">
            <i class="fas fa-car fa-3x mb-3 text-primary"></i>
            <h5 class="mb-2">Real-Time Availability</h5>
            <p>See and reserve parking spots instantly—no more guesswork.</p>
          </div>
        </div>
        <div class="col-md-4" data-aos="fade-up" data-aos-delay="200">
          <div class="feature-card p-4 text-center h-100">
            <i class="fas fa-map-marker-alt fa-3x mb-3 text-success"></i>
            <h5 class="mb-2">Multi-Location</h5>
            <p>Find parking in multiple lots across the city from one app.</p>
          </div>
        </div>
        <div class="col-md-4" data-aos="fade-up" data-aos-delay="300">
          <div class="feature-card p-4 text-center h-100">
            <i class="fas fa-credit-card fa-3x mb-3 text-warning"></i>
            <h5 class="mb-2">Secure Payments</h5>
            <p>Pay online with full encryption and get instant confirmation.</p>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- FOOTER -->
  <footer class="text-center py-4 text-white-50 bg-dark">
    &copy; 2025 ParkMee. All rights reserved.
  </footer>

  <!-- Scripts -->
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
  ></script>
  <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/landing.js"></script>
</body>
</html>
