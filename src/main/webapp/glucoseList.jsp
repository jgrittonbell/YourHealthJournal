<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/head.jsp" %>

<main class="container mt-5">
    <h2 class="mb-4">Glucose Readings</h2>

    <c:if test="${glucoseSuccess}">
        <div class="alert alert-success" role="alert">
            Glucose reading saved successfully!
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty readings}">
            <p>No glucose readings available.</p>
        </c:when>
        <c:otherwise>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Date</th>
                    <th scope="col">Time</th>
                    <th scope="col">Level (mg/dL)</th>
                    <th scope="col">Source</th>
                    <th scope="col">Notes</th>
                    <th scope="col">Edit</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="reading" items="${readings}">
                    <tr>
                        <td><c:out value="${reading.measurementTime.toLocalDate()}" /></td>
                        <td><c:out value="${reading.measurementTime.toLocalTime()}" /></td>
                        <td><c:out value="${reading.glucoseLevel}" /></td>
                        <td><c:out value="${reading.measurementSource}" /></td>
                        <td><c:out value="${reading.notes}" /></td>
                        <td>
                            <a href="glucose?action=edit&id=${reading.id}" class="btn btn-sm btn-outline-primary">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-secondary mt-3">Back to Dashboard</a>
</main>

<%@ include file="includes/footer.jsp" %>

