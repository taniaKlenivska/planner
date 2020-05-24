package hibernate;

import org.hibernate.Session;

public interface HibernateFunction {

    /**
     * Save object to database
     *
     * @param entity
     */
    boolean save(Session session, Object entity);

    /**
     * Update object
     *
     * @param entity
     */
    boolean update(Session session, Object entity);

    /**
     * Delete object from database
     *
     * @param entity
     */
    void delete(Session session, Object entity);

}
