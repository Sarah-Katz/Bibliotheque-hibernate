package Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPA {
	private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
	private static EntityManagerFactory factory;
	private static EntityManager em;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return factory;
	}

	public static void shutdownFactory() {
		if (factory != null) {
			factory.close();
		}
	}

	public static EntityManager getEntityManager() {
		if (em == null) {
			factory = getEntityManagerFactory();
			em = factory.createEntityManager();
		}
		return em;
	}

	public static void shutdownEntityManager() {
		if (em != null) {
			em.close();
		}
	}
}