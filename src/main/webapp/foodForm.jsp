<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/head.jsp" %>

<main class="container mt-5">
    <h2 class="mb-4">Add a New Food</h2>

    <form action="${pageContext.request.contextPath}/foods" method="post">
        <input type="hidden" name="action" value="insert" />

        <div class="mb-3">
            <label for="foodName" class="form-label">Food Name</label>
            <input type="text" class="form-control" id="foodName" name="foodName" required>
        </div>

        <div class="mb-3">
            <label for="calories" class="form-label">Calories</label>
            <input type="number" step="1.00" class="form-control" id="calories" name="calories" required>
        </div>

        <div class="mb-3">
            <label for="carbs" class="form-label">Carbs (g)</label>
            <input type="number" step="1.00" class="form-control" id="carbs" name="carbs" required>
        </div>

        <div class="mb-3">
            <label for="protein" class="form-label">Protein (g)</label>
            <input type="number" step="1.00" class="form-control" id="protein" name="protein" required>
        </div>

        <div class="mb-3">
            <label for="fat" class="form-label">Fat (g)</label>
            <input type="number" step="1.00" class="form-control" id="fat" name="fat" required>
        </div>

        <button type="submit" class="btn btn-success">Save Food</button>
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-secondary ms-2">Cancel</a>
    </form>
</main>

<%@ include file="includes/footer.jsp" %>
