<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:import url="includes/head.jsp" />
<body>
<div class="container mt-5">

    <div class="text-center">
        <h1 class="mb-4">Welcome to YourHealthJournal</h1>
        <p class="lead">
            <c:choose>
                <c:when test="${not empty sessionScope.username}">
                    Hello, <strong>${sessionScope.username}</strong>!
                </c:when>
                <c:otherwise>
                    Hello!
                </c:otherwise>
            </c:choose>
        </p>
    </div>

    <div class="row justify-content-center mt-4">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Track smarter. Live better.</h5>
                    <p class="card-text">
                        YourHealthJournal helps you record your meals and monitor your blood glucose levels in one place.
                        The goal of this journal is to put all of your data in one place that's accessible to only you. When you have more data,
                        you can make better choices and live happier.
                    </p>
                    <p class="card-text">
                        You can:
                    <ul>
                        <li>
                            Search meals and scan barcodes (Powered by
                            <a href="https://www.nutritionix.com/business/api" target="_blank">Nutritionix API</a>)
                        </li>
                        <li>Custom Entry for foods</li>
                        <li>Connect to Dexcom or manually log glucose</li>
                        <li>Visualize Your Data</li>
                    </ul>
                    </p>

                    <hr>
                    <div class="text-center mt-4">
                        <a href="logIn" class="btn btn-primary me-2">Login or Create New Account</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<c:import url="includes/footer.jsp" />
</body>
</html>
