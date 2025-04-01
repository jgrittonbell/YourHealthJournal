package com.grittonbelldev.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

//@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No setup needed for now
    }

    @Override
    public void destroy() {
        // No cleanup needed for now
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        //String contextPath = req.getContextPath(); //This might be used in prod
        String path = req.getRequestURI();
        //String relativePath = path.substring(contextPath.length()); // This might be used in prod

        boolean loggedIn = session != null && session.getAttribute("user") != null;

        boolean isPublicPath =
                        path.equals("/") ||
                        path.endsWith("/YourHealthJournal_war/") || //TODO remove this for prod --For local use only
                        path.contains("/logIn") ||
                        path.contains("/auth") ||
                        path.contains("/error.jsp") ||
                        path.contains("/css/") ||
                        path.contains("/js/") ||
                        path.contains("/images/") ||
                        path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".ico");

        if (loggedIn || isPublicPath) {
            chain.doFilter(request, response); // Let it through
        } else {
            resp.sendRedirect(req.getContextPath() + "/logIn");
        }
    }
}
