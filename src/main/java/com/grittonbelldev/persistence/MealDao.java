package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Meal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO class for performing custom database queries related to Meal entities.
 * <p>
 * Unlike GenericDAO, this class supports complex queries specific to the Meal domain,
 * such as filtering by time range or retrieving meals with related food metadata.
 * </p>
 */
public class MealDao {

    private static final Logger logger = LogManager.getLogger(NutritionixDao.class);

    /**
     * Fetches meals eaten within the last N days by user ID.
     *
     * @param userId The user's internal database ID (primary key)
     * @param days Number of days back from today
     * @return List of meals within that range
     */
    public List<Meal> findMealsWithinDays(Long userId, int days) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            String hql = "FROM Meal m WHERE m.user.id = :userId AND m.timeEaten >= :cutoff ORDER BY m.timeEaten DESC";

            LocalDate cutoffDate = LocalDate.now().minusDays(days);
            LocalDateTime cutoff = cutoffDate.atStartOfDay();

            logger.debug("Querying recent meals for userId={} within last {} days", userId, days);
            logger.debug("Cutoff date (at start of day): {}", cutoff);

            Query<Meal> query = session.createQuery(hql, Meal.class);
            query.setParameter("userId", userId);
            query.setParameter("cutoff", cutoff);

            List<Meal> result = query.list();

            logger.debug("Found {} meals after cutoff", result.size());
            for (Meal meal : result) {
                logger.debug("Meal ID: {}, timeEaten: {}", meal.getId(), meal.getTimeEaten());
            }

            return result;
        }
    }
}
