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
    private String clientId;
    private String loginUrl;
    private String redirectUrl;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        clientId = (String) context.getAttribute("client.id");
        loginUrl = (String) context.getAttribute("loginURL");
        redirectUrl = (String) context.getAttribute("redirectURL");
        logger.info("Attempting to use login servlet and below is the client id and the login URL and the redirect URL:");
        logger.info("Client ID: {}", clientId);
        logger.info("Login URL: {}", loginUrl);
        logger.info("Redirect URL: {}", redirectUrl);
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
        if (loginUrl == null || clientId == null || redirectUrl == null) {
            req.setAttribute("errorMessage", "Authentication service is unavailable. Please try again later.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }
        String url = loginUrl + "?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUrl;
        logger.info("Here is the redirect URL: {}", url);
        resp.sendRedirect(url);
    }
}
