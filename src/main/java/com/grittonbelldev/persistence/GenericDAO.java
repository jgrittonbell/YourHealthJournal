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
    private Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Instantiates a new DAO class
     *
     * @param type the entity type, for example User.
     */
    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    /**
     * Returns an open session for the SessionFactory
     * @return session
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }

    /**
     * Gets entity by id
     *
     * @param id entity id to search by
     * @return an entity
     */
    public <T>T getById(long id) {
        Session session = getSession();
        T entity = (T)session.get(type, id);
        session.close();
        return entity;
    }

    /**
     * Updates an entity
     *
     * @param entity The entity to be updated
     */
    public void update(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);// Merge method is only for updates
        transaction.commit();
        session.close();
    }

    /**
     * Insert an entity.
     *
     * @param entity the entity to be inserted
     * @return id the id of the entity that was inserted
     */
    public T insert(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.refresh(entity); // Ensure Hibernate assigns the ID
        session.close();
        return entity;
    }

    /**
     * Deletes the entity
     *
     * @param entity entity to be deleted
     */
    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }

    /**
     * Gets all entities
     *
     * @return all the entities
     */
    public List<T> getAll() {

        Session session = getSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        List<T> entities = session.createSelectionQuery( query ).getResultList();

        logger.debug("The list of entities " + entities);
        session.close();

        return entities;
    }

    /**
     * Gets a list of entities by a property value (exact match)
     *
     * @param propertyName the property to search by
     * @param value the value to match
     * @return list of matching entities
     */
    public List<T> getByPropertyEqual(String propertyName, Object value) {
        Session session = getSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);

        query.select(root).where(builder.equal(root.get(propertyName), value));

        List<T> results = session.createQuery(query).getResultList();
        session.close();
        return results;
    }



    /**
     * Gets a list of entities where a property contains a given value (LIKE %value%)
     *
     * @param propertyName the property to search by
     * @param value the partial value to match
     * @return list of matching entities
     */
    public List<T> getByPropertyLike(String propertyName, String value) {
        Session session = getSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);

        query.select(root).where(builder.like(root.get(propertyName), "%" + value + "%"));

        List<T> results = session.createQuery(query).getResultList();
        session.close();
        return results;
    }
}
