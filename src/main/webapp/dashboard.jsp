<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/head.jsp" %>

<main class="container mt-5">
  <h1 class="mb-4">Welcome to YourHealthJournal</h1>

  <c:choose>
    <c:when test="${not empty sessionScope.user}">
      <p>Hello, <strong>${sessionScope.user.firstName} ${sessionScope.user.lastName}</strong>!</p>
      <p>You are successfully logged in and viewing your secure dashboard.</p>
      <form action="foods" method="get" style="display:inline;">
        <input type="hidden" name="action" value="create">
        <button type="submit" class="btn btn-primary">Add Food</button>
      </form>

      <form action="glucose?action=create" method="get" style="display:inline;">
        <button type="submit" class="btn btn-secondary">Add Glucose Reading</button>
      </form>
    </c:when>
    <c:otherwise>
      <p>Error: User information not available. Please <a href="auth">log in</a>.</p>
    </c:otherwise>
  </c:choose>

</main>

<%@ include file="includes/footer.jsp" %>
