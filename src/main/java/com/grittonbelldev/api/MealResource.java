package com.grittonbelldev.api;

import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/meals")
public class MealResource {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Context
    private HttpServletRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMeals() {
        logger.info("getMeals called");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"User not logged in\"}")
                    .build();
        }

        GenericDAO<Meal> mealDao = new GenericDAO<>(Meal.class);
        List<Meal> meals = mealDao.getByPropertyEqual("user", user);

        return Response.status(200).entity(meals).build();
    }
}
