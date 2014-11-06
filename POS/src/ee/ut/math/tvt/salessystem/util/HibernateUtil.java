package ee.ut.math.tvt.salessystem.util;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Utility class that makes sure we has a single open hibernate session.
 */
public class HibernateUtil {
	private static final Logger log = Logger.getLogger(HibernateUtil.class);

	private static ServiceRegistry serviceRegistry;
	public static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration();
			configuration.configure();

			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			log.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static Session session;

	public static Session currentSession() throws HibernateException {
		// Open a new Session, if this thread has none yet
		if (session == null) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	public static void closeSession() throws HibernateException {
		if (session != null)
			session.close();
		session = null;
	}

}