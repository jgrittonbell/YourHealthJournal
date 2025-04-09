package com.grittonbelldev.persistence;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * Generic Data Access Object (DAO) class for performing common CRUD operations on any entity type.
 *
 * @param <T> The entity type that this DAO will manage.
 */
public class GenericDAO<T> {
    private final Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Constructs a new GenericDAO for the given entity class.
     *
     * @param type The class type of the entity.
     */
    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    /**
     * Retrieves an entity by its primary key.
     *
     * @param id The primary key value.
     * @param <ID> The type of the primary key.
     * @return The entity with the given ID, or null if not found.
     */
    public <ID> T getById(ID id) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.get(type, id);
        }
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity The entity to update.
     */
    public void update(T entity) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
    }

    /**
     * Inserts a new entity into the database and refreshes it to retrieve its generated ID.
     *
     * @param entity The entity to insert.
     * @return The inserted and refreshed entity.
     */
    public T insert(T entity) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.refresh(entity);
            return entity;
        }
    }

    /**
     * Deletes an existing entity from the database.
     *
     * @param entity The entity to delete.
     */
    public void delete(T entity) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }

    /**
     * Retrieves all entities of the given type from the database.
     *
     * @return A list of all entities.
     */
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

    /**
     * Retrieves all entities where the given property equals the specified value.
     * Supports nested properties using dot notation (e.g., "user.id").
     *
     * @param propertyName The name of the property (can be nested).
     * @param value The value to compare against.
     * @return A list of matching entities.
     */
    public List<T> getByPropertyEqual(String propertyName, Object value) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);

            Path<?> path = buildPath(root, propertyName);
            query.select(root).where(builder.equal(path, value));

            return session.createQuery(query).getResultList();
        }
    }

    /**
     * Retrieves all entities where the given string property contains the specified substring.
     * Supports nested properties using dot notation (e.g., "user.email").
     *
     * @param propertyName The name of the string property (can be nested).
     * @param value The substring to search for.
     * @return A list of matching entities.
     */
    public List<T> getByPropertyLike(String propertyName, String value) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);

            Path<String> path = buildPath(root, propertyName);
            query.select(root).where(builder.like(path, "%" + value + "%"));

            return session.createQuery(query).getResultList();
        }
    }

    /**
     * Helper method that builds a JPA Path from a dot-separated property string.
     * For example, "user.id" becomes root.get("user").get("id").
     *
     * @param root The root of the criteria query.
     * @param propertyPath A dot-separated path to the desired property.
     * @param <X> The expected type of the property.
     * @return The constructed Path object.
     */
    private <X> Path<X> buildPath(Root<T> root, String propertyPath) {
        String[] parts = propertyPath.split("\\.");
        Path<?> path = root;
        for (String part : parts) {
            path = path.get(part); // Traverse through each level
        }
        @SuppressWarnings("unchecked")
        Path<X> finalPath = (Path<X>) path;
        return finalPath;
    }
}
