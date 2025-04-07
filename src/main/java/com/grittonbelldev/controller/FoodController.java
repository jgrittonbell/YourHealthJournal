package com.grittonbelldev.controller;

import com.grittonbelldev.entity.Food;
import com.grittonbelldev.persistence.GenericDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/foods")
public class FoodController extends HttpServlet {
    private final GenericDAO<Food> foodDao = new GenericDAO<>(Food.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equalsIgnoreCase(action)) {
            req.getRequestDispatcher("foodForm.jsp").forward(req, resp);
        } else {
            // Optional: forward to a food list page or dashboard
            resp.sendRedirect("dashboard.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("insert".equalsIgnoreCase(action)) {
            insertFood(req, resp);
        } else {
            resp.sendRedirect("dashboard.jsp");
        }
    }

    private void insertFood(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String foodName = req.getParameter("foodName");
        double calories = Double.parseDouble(req.getParameter("calories"));
        double carbs = Double.parseDouble(req.getParameter("carbs"));
        double protein = Double.parseDouble(req.getParameter("protein"));
        double fat = Double.parseDouble(req.getParameter("fat"));

        Food food = new Food(foodName, fat, protein, carbs, calories);
        foodDao.insert(food);

        resp.sendRedirect("dashboard.jsp");
    }
}

