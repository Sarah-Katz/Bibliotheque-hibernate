package LibraryApp;

import javax.persistence.EntityManager;

public class Main {

	public static void main(String[] args) {
		Menu.mainMenu();
	}

	private static void testLivre() {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		Livre test = new Livre("Lol", "htftfh", "rthth", 12322, "AA0002");
		Reserv testres = new Reserv(null, null, test);
		entityManager.persist(testres);
		entityManager.persist(test);
		entityManager.getTransaction().commit();
		entityManager.close();
		JPAUtil.shutdown();
	}
}