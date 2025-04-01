<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Register Your Account</title>
</head>
<body>
<h1>Finish Setting Up Your Account</h1>

<form action="registerUser" method="post">
  <label for="firstName">First Name:</label><br>
  <input type="text" id="firstName" name="firstName" required><br><br>

  <label for="lastName">Last Name:</label><br>
  <input type="text" id="lastName" name="lastName" required><br><br>

  <label for="email">Email:</label><br>
  <input type="email" id="email" name="email"
         value="${sessionScope.email}" required><br><br>

  <input type="submit" value="Register">
</form>
</body>
</html>
