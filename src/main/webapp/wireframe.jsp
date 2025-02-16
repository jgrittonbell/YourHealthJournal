<%--
  Created by IntelliJ IDEA.
  User: Justin
  Date: 2/12/2025
  Time: 2:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Health Journal - Wireframes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Your Health Journal</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="#login">Login</a></li>
                <li class="nav-item"><a class="nav-link" href="#dashboard">Dashboard</a></li>
                <li class="nav-item"><a class="nav-link" href="#food-log">Log Meal</a></li>
                <li class="nav-item"><a class="nav-link" href="#glucose-log">Log Glucose</a></li>
            </ul>
        </div>
    </nav>

    <!-- Login Page -->
    <section id="login" class="my-4">
        <h2>Login / Sign Up</h2>
        <form>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" required>
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
            <p class="mt-2"><a href="#">Forgot Password?</a></p>
        </form>
    </section>

    <!-- Dashboard -->
    <section id="dashboard" class="my-4">
        <h2>Dashboard</h2>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Recent Meals</h5>
                <p class="card-text">View your recently logged meals.</p>
            </div>
        </div>
        <div class="card mt-2">
            <div class="card-body">
                <h5 class="card-title">Recent Glucose Readings</h5>
                <p class="card-text">Check your latest glucose readings.</p>
            </div>
        </div>
    </section>

    <!-- Food Logging Page -->
    <section id="food-log" class="my-4">
        <h2>Log a Meal</h2>
        <div class="accordion" id="foodLogAccordion">
            <div class="accordion-item">
                <h2 class="accordion-header" id="searchDatabaseHeading">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#searchDatabase" aria-expanded="true">
                        Search Database
                    </button>
                </h2>
                <div id="searchDatabase" class="accordion-collapse collapse show" aria-labelledby="searchDatabaseHeading" data-bs-parent="#foodLogAccordion">
                    <div class="accordion-body">
                        <input type="text" class="form-control" placeholder="Search for food...">
                        <button class="btn btn-success mt-2">Search</button>
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header" id="enterManuallyHeading">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#enterManually" aria-expanded="false">
                        Enter Manually
                    </button>
                </h2>
                <div id="enterManually" class="accordion-collapse collapse" aria-labelledby="enterManuallyHeading" data-bs-parent="#foodLogAccordion">
                    <div class="accordion-body">
                        <input type="text" class="form-control" placeholder="Food Name">
                        <input type="number" class="form-control mt-2" placeholder="Calories">
                        <input type="number" class="form-control mt-2" placeholder="Carbs (g)">
                        <input type="number" class="form-control mt-2" placeholder="Protein (g)">
                        <input type="number" class="form-control mt-2" placeholder="Fat (g)">
                        <button class="btn btn-info mt-2">Add Meal</button>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Glucose Logging Page -->
    <section id="glucose-log" class="my-4">
        <h2>Log Glucose</h2>
        <form>
            <div class="mb-3">
                <label for="glucose" class="form-label">Glucose Level (mg/dL)</label>
                <input type="number" class="form-control" id="glucose" required>
            </div>
            <button type="submit" class="btn btn-primary">Log Reading</button>
        </form>
    </section>

    <!-- Food Journal -->
    <section id="food-journal" class="my-4">
        <h2>Food Journal</h2>
        <p>View and manage your logged meals.</p>
    </section>

    <!-- Glucose History -->
    <section id="glucose-history" class="my-4">
        <h2>Glucose History</h2>
        <p>View and manage your glucose readings.</p>
    </section>

    <!-- Meal & Glucose Comparison -->
    <section id="comparison" class="my-4">
        <h2>Meal & Glucose Comparison</h2>
        <p>Analyze how your meals impact glucose levels.</p>
    </section>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
