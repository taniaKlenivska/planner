package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionConfig {

    private static SessionFactory sessionFactory = null;

    /**
     * Build hibernate settings
     *
     * @return
     */
    public static boolean buildSessionFactory() {
        try {
            // Create the SessionFactory from configuration.hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate/hibernate.cfg.xml");
            System.out.println("Hibernate Configuration loaded");
            sessionFactory = configuration.buildSessionFactory();
            return true;
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            sessionFactory = null;
            return false;
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
