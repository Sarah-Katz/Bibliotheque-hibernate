package Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * The JPA class provides utility methods for working with the Java Persistence
 * API (JPA). It includes methods for creating and closing an
 * EntityManagerFactory and an EntityManager. The EntityManagerFactory is used
 * to create EntityManager objects, which are used to interact with the database
 * and perform persistence operations such as creating, reading, updating, and
 * deleting entity objects.
 * 
 * @author Sarah Katz
 *
 */
public class JPA {
	/** The name of the persistence unit. */
	private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
	/** The EntityManagerFactory used to create EntityManager objects. */
	private static EntityManagerFactory factory;
	/** The EntityManager used to interact with the database. */
	private static EntityManager em;

	/**
	 * Returns the EntityManagerFactory used to create EntityManager objects. If the
	 * factory has not yet been created, it will be created using the persistence
	 * unit name specified in the PERSISTENCE_UNIT_NAME field.
	 * 
	 * @return the EntityManagerFactory
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return factory;
	}

	/** Closes the EntityManagerFactory if it is not already closed. */
	public static void shutdownFactory() {
		if (factory != null) {
			factory.close();
		}
	}

	/**
	 * Returns the EntityManager used to interact with the database. If the
	 * EntityManager has not yet been created, it will be created using the
	 * EntityManagerFactory.
	 * 
	 * @return the EntityManager
	 */
	public static EntityManager getEntityManager() {
		if (em == null) {
			factory = getEntityManagerFactory();
			em = factory.createEntityManager();
		}
		return em;
	}

	/** Closes the EntityManager if it is not already closed. */
	public static void shutdownEntityManager() {
		if (em != null) {
			em.close();
		}
	}
}