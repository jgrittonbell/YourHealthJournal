<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:import url="includes/head.jsp" />
<body>
<div class="container mt-5">
    <div class="alert alert-danger text-center shadow-sm p-4 rounded">
        <h4 class="alert-heading">Oops! Something went wrong.</h4>
        <p>
            <c:out value="${errorMessage}" default="An unexpected error occurred." />
        </p>
        <hr>
        <a href="logIn" class="btn btn-outline-primary">Back to Login</a>
    </div>
</div>
<c:import url="includes/footer.jsp" />
</body>
</html>
