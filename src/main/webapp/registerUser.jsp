<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/head.jsp" %>

<main class="container mt-5">
  <h2 class="mb-4">Complete Your Registration</h2>

  <c:if test="${not empty requestScope.errorMessage}">
    <div class="alert alert-danger">${requestScope.errorMessage}</div>
  </c:if>

  <form action="registerUser" method="post">
    <div class="mb-3">
      <label for="firstName" class="form-label">First Name</label>
      <input type="text" id="firstName" name="firstName" class="form-control" required>
    </div>

    <div class="mb-3">
      <label for="lastName" class="form-label">Last Name</label>
      <input type="text" id="lastName" name="lastName" class="form-control" required>
    </div>

    <div class="mb-3">
      <label for="email" class="form-label">Email (read-only)</label>
      <input type="email" id="email" name="email" class="form-control" value="${sessionScope.email}" readonly>
    </div>

    <button type="submit" class="btn btn-primary">Register</button>
  </form>
</main>

<%@ include file="includes/footer.jsp" %>
