package com.grittonbelldev.persistence;

import com.grittonbelldev.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

public class GenericDAO<T> {
    private Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    public <ID> T getById(ID id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T entity = session.get(type, id);
        session.close();
        return entity;
    }

    public void update(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        session.close();
    }

    public T insert(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.refresh(entity);
        session.close();
        return entity;
    }

    public void delete(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }

    public List<T> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        List<T> entities = session.createSelectionQuery(query).getResultList();
        logger.debug("The list of entities " + entities);
        session.close();
        return entities;
    }

    public List<T> getByPropertyEqual(String propertyName, Object value) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<T> results = session.createQuery(query).getResultList();
        session.close();
        return results;
    }

    public List<T> getByPropertyLike(String propertyName, String value) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root).where(builder.like(root.get(propertyName), "%" + value + "%"));
        List<T> results = session.createQuery(query).getResultList();
        session.close();
        return results;
    }
}
