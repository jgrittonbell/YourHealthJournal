package com.grittonbelldev.persistence;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

public class GenericDAO<T> {
    private final Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    public <ID> T getById(ID id) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.get(type, id);
        }
    }

    public void update(T entity) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
    }

    public T insert(T entity) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.refresh(entity);
            return entity;
        }
    }

    public void delete(T entity) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }

    public List<T> getAll() {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            query.from(type);
            List<T> entities = session.createSelectionQuery(query).getResultList();
            logger.debug("The list of entities: " + entities);
            return entities;
        }
    }

    public List<T> getByPropertyEqual(String propertyName, Object value) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.select(root).where(builder.equal(root.get(propertyName), value));
            return session.createQuery(query).getResultList();
        }
    }

    public List<T> getByPropertyLike(String propertyName, String value) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.select(root).where(builder.like(root.get(propertyName), "%" + value + "%"));
            return session.createQuery(query).getResultList();
        }
    }
}
