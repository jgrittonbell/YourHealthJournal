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

/**
 * GlucoseController handles CRUD actions for glucose readings.
 * Supports creation, listing, editing, and updating readings.
 */
@WebServlet("/glucose")
public class GlucoseController extends HttpServlet {

    private final GenericDAO<GlucoseReading> glucoseDao = new GenericDAO<>(GlucoseReading.class);

    /**
     * Handles HTTP GET requests for listing, creating, or editing glucose readings.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        // If user clicked "Add Glucose Reading"
        if ("create".equalsIgnoreCase(action)) {
            req.getRequestDispatcher("glucoseForm.jsp").forward(req, resp);

            // If user clicked "Edit" from the list view
        } else if ("edit".equalsIgnoreCase(action)) {
            String idParam = req.getParameter("id");

            // Validate and parse the reading ID
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                GlucoseReading reading = glucoseDao.getById(id);

                // If the reading exists, forward it to the form pre-filled
                if (reading != null) {
                    req.setAttribute("reading", reading);
                    req.getRequestDispatcher("glucoseForm.jsp").forward(req, resp);
                    return;
                }
            }

            // Fallback if ID is missing or invalid
            resp.sendRedirect("glucose?action=list");

            // If user is viewing the list of glucose readings
        } else if ("list".equalsIgnoreCase(action)) {
            listGlucoseReadings(req, resp);

            // Default/fallback behavior: go back to dashboard
        } else {
            resp.sendRedirect("dashboard.jsp");
        }
    }

    /**
     * Handles HTTP POST requests for inserting or updating glucose readings.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        // User is submitting a new glucose reading
        if ("insert".equalsIgnoreCase(action)) {
            insertGlucose(req, resp);

            // User is updating an existing glucose reading
        } else if ("update".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            GlucoseReading reading = glucoseDao.getById(id);

            // Proceed only if the reading exists
            if (reading != null) {
                double glucoseLevel = Double.parseDouble(req.getParameter("glucoseLevel"));
                String measurementSource = req.getParameter("measurementSource");
                String notes = req.getParameter("notes");

                reading.setGlucoseLevel(glucoseLevel);
                reading.setMeasurementSource(measurementSource);
                reading.setNotes(notes);

                glucoseDao.update(reading);

                // Flag the update for feedback on list page
                req.getSession().setAttribute("glucoseSuccess", true);
            }

            // Redirect to list page after update
            resp.sendRedirect("glucose?action=list");

            // Fallback to dashboard for any unknown action
        } else {
            resp.sendRedirect("dashboard.jsp");
        }
    }

    /**
     * Inserts a new glucose reading into the database and redirects to the list.
     */
    private void insertGlucose(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        double glucoseLevel = Double.parseDouble(req.getParameter("glucoseLevel"));
        String measurementSource = req.getParameter("measurementSource");
        String notes = req.getParameter("notes");

        GlucoseReading reading = new GlucoseReading(user, glucoseLevel, LocalDateTime.now(), measurementSource, notes);
        glucoseDao.insert(reading);

        HttpSession session = req.getSession();
        session.setAttribute("glucoseSuccess", true);

        resp.sendRedirect("glucose?action=list");
    }

    /**
     * Displays the list of glucose readings for the current user.
     * Also handles optional success feedback messages.
     */
    private void listGlucoseReadings(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        List<GlucoseReading> readings = glucoseDao.getByPropertyEqual("user.id", user.getId());

        HttpSession session = req.getSession();
        if (session.getAttribute("glucoseSuccess") != null) {
            req.setAttribute("glucoseSuccess", true);
            session.removeAttribute("glucoseSuccess");
        }

        req.setAttribute("readings", readings);
        req.getRequestDispatcher("glucoseList.jsp").forward(req, resp);
    }
}
