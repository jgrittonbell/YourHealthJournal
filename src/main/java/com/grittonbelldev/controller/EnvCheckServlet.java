package com.grittonbelldev.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/env-check")
public class EnvCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        String dbUser = System.getenv("mySQLUsername");
        String dbUrl = System.getenv("mySQLURL");
        String cognitoClientId = System.getenv("cognitoClientID");

        response.getWriter().println("ENV CHECK:");
        response.getWriter().println("DB_USER: " + (dbUser != null ? dbUser : "[NOT SET]"));
        response.getWriter().println("DB_URL: " + (dbUrl != null ? dbUrl : "[NOT SET]"));
        response.getWriter().println("COGNITO_CLIENT_ID: " + (cognitoClientId != null ? cognitoClientId : "[NOT SET]"));
    }
}
