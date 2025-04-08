package com.grittonbelldev.controller;

import com.grittonbelldev.entity.GlucoseReading;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/glucose")
public class GlucoseController extends HttpServlet {
    private final GenericDAO<GlucoseReading> glucoseDao = new GenericDAO<>(GlucoseReading.class);
    private final GenericDAO<User> userDao = new GenericDAO<>(User.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equalsIgnoreCase(action)) {
            req.getRequestDispatcher("glucoseForm.jsp").forward(req, resp);
        } else if ("list".equalsIgnoreCase(action)) {
            listGlucoseReadings(req, resp);
        } else {
            resp.sendRedirect("dashboard.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("insert".equalsIgnoreCase(action)) {
            insertGlucose(req, resp);
        } else {
            resp.sendRedirect("dashboard.jsp");
        }
    }

    private void insertGlucose(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cognitoId = (String) req.getSession().getAttribute("cognitoId");
        User user = userDao.getById(cognitoId);

        double glucoseLevel = Double.parseDouble(req.getParameter("glucoseLevel"));
        String measurementSource = req.getParameter("measurementSource");
        String notes = req.getParameter("notes");

        GlucoseReading reading = new GlucoseReading(user, glucoseLevel, LocalDateTime.now(), measurementSource, notes);
        glucoseDao.insert(reading);

        HttpSession session = req.getSession();
        session.setAttribute("glucoseSuccess", true);

        resp.sendRedirect("glucose?action=list");
    }

    private void listGlucoseReadings(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cognitoId = (String) req.getSession().getAttribute("cognitoId");
        List<GlucoseReading> readings = glucoseDao.getByPropertyEqual("user.id", cognitoId);
        req.setAttribute("readings", readings);
        req.getRequestDispatcher("glucoseList.jsp").forward(req, resp);
    }
}
