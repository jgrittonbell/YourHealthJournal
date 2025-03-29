<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <c:import url="includes/head.jsp" />
<body>
<main class="container mt-4">
  <h1 class="mb-3">Your Foods</h1>

  <table class="table table-striped table-bordered">
    <thead class="thead-dark">
    <tr>
      <th>Name</th>
      <th>Calories</th>
      <th>Protein (g)</th>
      <th>Carbs (g)</th>
      <th>Fat (g)</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="food" items="${foods}">
      <tr>
        <td>${food.foodName}</td>
        <td>${food.calories}</td>
        <td>${food.protein}</td>
        <td>${food.carbs}</td>
        <td>${food.fat}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</main>
<c:import url="includes/footer.jsp" />
</body>
</html>
