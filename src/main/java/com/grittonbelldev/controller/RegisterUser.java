package com.grittonbelldev.controller;

import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/registerUser")
public class RegisterUser extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("There is a get request for the registerUser servlet");
        req.getRequestDispatcher("registerUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("There is a post request for the registerUser servlet");
        GenericDAO<User> userDao = new GenericDAO<>(User.class);
        HttpSession session = req.getSession();

        String cognitoId = (String) session.getAttribute("cognitoId");
        String email = (String) session.getAttribute("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        if (cognitoId == null || email == null || firstName == null || lastName == null) {
            logger.error("Missing user data in session or form submission");
            req.setAttribute("errorMessage", "Missing required information. Please try again.");
            req.getRequestDispatcher("registerUser.jsp").forward(req, resp);
            return;
        }

        // Check for existing user (by Cognito ID)
        if (!userDao.getByPropertyEqual("cognitoId", cognitoId).isEmpty()) {
            logger.warn("Attempt to register existing Cognito ID: {}", cognitoId);
            req.setAttribute("errorMessage", "User already exists.");
            req.getRequestDispatcher("registerUser.jsp").forward(req, resp);
            return;
        }

        // Create and insert the new user
        User newUser = new User(cognitoId, firstName, lastName, email);
        userDao.insert(newUser);

        logger.info("Successfully registered user: {} ({})", email, cognitoId);
        session.setAttribute("user", newUser);

        // Redirect to a secure dashboard
        resp.sendRedirect("dashboard.jsp");
    }
}
