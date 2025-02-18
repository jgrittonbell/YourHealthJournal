package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Food;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

public class FoodDAO {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    public Food getById (int id) {
        Session session = sessionFactory.openSession();
        Food food = session.get(Food.class, id);
        session.close();
        return food;
    }

    public void update (Food food) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(food);
        tx.commit();
        session.close();
    }

    public long insert (Food food) {
        long id = -1;
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(food);
        tx.commit();
        id = food.getId();
        session.close();
        return id;
    }

    public void delete (Food food) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(food);
        tx.commit();
        session.close();
    }

    public List<Food> getAll() {
        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Food> criteria = builder.createQuery(Food.class);
        criteria.from(Food.class);
        List<Food> foods = session.createQuery(criteria).getResultList();

        logger.info(foods.toString());
        session.close();
        return foods;
    }

    public List<Food> getByPropertyEqual (String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for user with {} {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Food> criteria = builder.createQuery(Food.class);
        Root<Food> root = criteria.from(Food.class);
        criteria.where(builder.equal(root.get(propertyName), value));
        List<Food> foods = session.createQuery(criteria).getResultList();
        session.close();
        return foods;
    }

    public List<Food> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for user with {} {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Food> criteria = builder.createQuery(Food.class);
        Root<Food> root = criteria.from(Food.class);
        Expression<String> propertyPath = root.get(propertyName);

        criteria.where(builder.like(propertyPath, "%" + value + "%"));

        List<Food> foods = session.createQuery(criteria).getResultList();
        session.close();
        return foods;
    }
}
