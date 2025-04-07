<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/head.jsp" %>

<main class="container mt-5">
    <h2 class="mb-4">Add Glucose Reading</h2>

    <form action="${pageContext.request.contextPath}/glucose" method="post">
        <input type="hidden" name="action" value="insert" />
        <input type="hidden" name="measurementSource" value="Manual" />

        <div class="mb-3">
            <label for="glucoseLevel" class="form-label">Glucose Level (mg/dL)</label>
            <input type="number" step="1" class="form-control" id="glucoseLevel" name="glucoseLevel" required>
        </div>

        <div class="mb-3">
            <label for="notes" class="form-label">Notes (optional)</label>
            <textarea class="form-control" id="notes" name="notes" rows="3"></textarea>
        </div>

        <button type="submit" class="btn btn-success">Save Reading</button>
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-secondary ms-2">Cancel</a>
    </form>
</main>

<%@ include file="includes/footer.jsp" %>
