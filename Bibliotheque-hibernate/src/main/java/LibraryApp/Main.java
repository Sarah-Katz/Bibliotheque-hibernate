package LibraryApp;

import javax.persistence.EntityManager;

import Objects.DatesReserv;
import Objects.Livre;
import Utils.JPA;

public class Main {

	public static void main(String[] args) {
		Menu.mainMenu();
	}

	private static void testLivre() {
		EntityManager entityManager = JPA.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		Livre test = new Livre("Lol", "htftfh", "rthth", 12322, "AA0002");
		DatesReserv testres = new DatesReserv(null, null, test);
		entityManager.persist(testres);
		entityManager.persist(test);
		entityManager.getTransaction().commit();
		entityManager.close();
		JPA.shutdownFactory();
	}
}