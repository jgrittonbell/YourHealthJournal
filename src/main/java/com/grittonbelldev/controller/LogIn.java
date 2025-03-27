package com.grittonbelldev.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = {"/logIn"}
)

/** Begins the authentication process using AWS Cognito
 *
 */
public class LogIn extends HttpServlet{
    private final Logger logger = LogManager.getLogger(this.getClass());
    public static String CLIENT_ID;
    public static String LOGIN_URL;
    public static String REDIRECT_URL;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        CLIENT_ID = (String) context.getAttribute("client.id");
        LOGIN_URL = (String) context.getAttribute("loginURL");
        REDIRECT_URL = (String) context.getAttribute("redirectURL");
    }

    /**
     * Route to the aws-hosted cognito login page.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (LOGIN_URL == null || CLIENT_ID == null || REDIRECT_URL == null) {
            req.setAttribute("errorMessage", "Authentication service is unavailable. Please try again later.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }
        String url = LOGIN_URL + "?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL;
        resp.sendRedirect(url);
    }
}
