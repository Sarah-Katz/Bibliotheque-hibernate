package LibraryApp;

import javax.persistence.EntityManager;

public class Main {

	public static void main(String[] args) {
		testLivre();
	}
	
	private static void testLivre() {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Reserv testres = new Reserv(null, null);
        Livre test = new Livre("Lol", "htftfh", "rthth", 12322, "AA0002");
        entityManager.persist(testres);
        entityManager.persist(test);
        entityManager.getTransaction().commit();
//        entityManager.close();
//        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.getTransaction().commit();
        entityManager.close();
        JPAUtil.shutdown();
	}
	
	private static void testReserv() {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Reserv test = new Reserv(null, null);
        entityManager.persist(test);
        entityManager.getTransaction().commit();
        entityManager.close();
        JPAUtil.shutdown();
	}

}
