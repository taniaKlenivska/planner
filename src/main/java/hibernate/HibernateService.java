package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateService implements HibernateFunction {

    private static HibernateService hibernateService = new HibernateService();

    public static HibernateService getInstance() {
        return hibernateService;
    }

    private HibernateService() {
    }

    @Override
    public boolean save(Session session, Object entity) {
        try {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("HibernateException from save method: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Session session, Object entity) {
        try {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("HibernateException from update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void delete(Session session, Object entity) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("HibernateException from delete method: " + e.getMessage());
        }
    }
}
