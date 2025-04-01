<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="includes/head.jsp" />

<div class="container mt-5">
  <h2>Welcome to YourHealthJournal</h2>
  <p>Please complete your registration by providing the following information:</p>

  <form action="registerUser" method="post">
    <input type="hidden" name="cognitoId" value="${sessionScope.cognitoId}" />
    <input type="hidden" name="email" value="${sessionScope.email}" />

    <div class="mb-3">
      <label for="firstName" class="form-label">First Name:</label>
      <input type="text" class="form-control" name="firstName" id="firstName" required />
    </div>

    <div class="mb-3">
      <label for="lastName" class="form-label">Last Name:</label>
      <input type="text" class="form-control" name="lastName" id="lastName" required />
    </div>

    <button type="submit" class="btn btn-primary">Register</button>
  </form>
</div>

<jsp:include page="includes/footer.jsp" />
