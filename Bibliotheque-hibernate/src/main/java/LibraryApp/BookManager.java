package LibraryApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Objects.DatesReserv;
import Objects.Livre;
import Objects.Reservation;
import Utils.JPA;

/**
 * This class contains the methods used to add new books to the library
 *
 * @author Sarah Katz
 *
 */
public class BookManager {

	/**
	 * This method lets the user instance a new Book in bookList using parameters
	 * he'll input
	 *
	 * @param bookList List of books registered in the program
	 */
	protected static void newBook() {
		int pageNumber = 0;
		String ref = null;
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Retour menu : 'm'");
			System.out.println(
					"Bonjour, nous allons enregistrer votre livre, merci de renseigner les informations suivantes :");
			System.out.println("Titre du livre : ");
			String title = in.nextLine().toLowerCase();
			switch (title) {
			case "m":
				Menu.mainMenu();
			default:
				System.out.println("L'auteur.ice du livre :");
				String author = in.nextLine().toLowerCase();
				System.out.println("Le genre du livre :");
				String genre = in.nextLine().toLowerCase();
				try {
					System.out.println("Le nombre de page :");
					pageNumber = in.nextInt();
					in.nextLine();
					System.out.println("La reference du livre :");
					ref = in.nextLine().toLowerCase();
				} catch (InputMismatchException e) {
					System.out.println("____________________________________________");
					System.out.println("|L'information demandée doit être un nombre|");
					System.out.println("|__________________________________________|");
					newBook();
				}
				System.out.println("Veuillez confirmer les informations renseignées");
				StringBuilder result = new StringBuilder();
				result.append("Titre : ").append(title).append("\n").append("Auteur.ice : ").append(author).append("\n")
						.append("Genre : ").append(genre).append("\n").append("Nombre de pages : ").append(pageNumber)
						.append("\n").append("Référence : ").append(ref);
				confirmNewBook(title, author, genre, pageNumber, ref, result);
			}

		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			newBook();
		}
	}

	/**
	 * This method let's the user search a list of books by author name *
	 *
	 * @param bookList List of books registered in the program
	 */
	protected static void searchBook() {
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Retour menu : 'm'");
			System.out.println("");
			System.out.println("Veuillez entrer le nom de l'auteur.ice du livre recherché :");
			String searchedAuthor = in.nextLine().toLowerCase();
			switch (searchedAuthor) {
			case "m":
				Menu.mainMenu();
			default:
				EntityManager em = JPA.getEntityManager();
				@SuppressWarnings("unchecked")
				List<Livre> listeLivre = em.createNamedQuery("Livre.searchAuthor")
						.setParameter("inputTitle", "%" + searchedAuthor + "%").getResultList();
				StringBuilder result = new StringBuilder();
				if (listeLivre.size() > 0) {
					for (Livre l : listeLivre) {
						result.setLength(0);
						result.append("Titre : ").append(l.getTitre()).append(", Auteur.ice : ").append(l.getAuteur())
								.append(", Genre : ").append(l.getGenre()).append(", Nombre de pages : ")
								.append(l.getPages()).append(", Disponible : ");
						if (l.isDisponible()) {
							result.append("oui.");
						} else {
							result.append("non");
						}
						System.out.println(result);
						confirmSearchBook(listeLivre);
					}
				} else {
					System.out.println("_________________________________________________________________________");
					System.out.println("|Aucun livre par cet.te auteur.ice n'est disponible dans la bibliothèque|");
					System.out.println("|_______________________________________________________________________|");
					searchBook();
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			searchBook();
		}
	}

	/**
	 * This method will display infos about all books in the bookList param
	 *
	 * @param bookList List of books registered in the program
	 */
	protected static void showBookList() {
		EntityManager em = JPA.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Livre> livre = em.createNamedQuery("Livre.showAll").getResultList();
		StringBuilder result = new StringBuilder();
		for (Livre l : livre) {
			result.setLength(0);
			result.append("Titre : ").append(l.getTitre()).append(", Auteur.ice : ").append(l.getAuteur())
					.append(", Genre : ").append(l.getGenre()).append(", Nombre de pages : ").append(l.getPages())
					.append(", Disponible : ");
			if (l.isDisponible()) {
				result.append("oui.");
			} else {
				result.append("non");
			}
			System.out.println(result);
		}
		Menu.mainMenu();
	}

	// confirmation menu for newBook()
	private static void confirmNewBook(final String title, final String author, final String genre,
			final int pageNumber, final String ref, final StringBuilder result) {
		try {
			Scanner in = new Scanner(System.in);
			System.out.println(result);
			System.out.println("Confirmez vous les informations renseignéees ? (Oui : o / Non : n)");
			String confirm = in.next();
			confirm.toLowerCase();
			switch (confirm) {
			case "o":
				EntityManager em = JPA.getEntityManager();
				em.getTransaction().begin();
				System.out.println("Combien d'exemplaires souhaitez vous enregistrer ?");
				int bookCopies = in.nextInt();
				for (int i = 0; i < bookCopies; i++) {
					Livre livre = new Livre(title, author, genre, pageNumber, ref);
					em.persist(livre);
				}
				em.getTransaction().commit();
				System.out.println("Votre enregistrement est un succès.");
				Menu.mainMenu();
				break;
			case "n":
				newBook();
				break;
			default:
				System.out.println("entrée invalide");
				confirmNewBook(title, author, genre, pageNumber, ref, result);
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			Menu.mainMenu();
		}
	}

	// confirmation menu for searchBook()
	private static void confirmSearchBook(final List<Livre> listeLivre) {
		try {
			Scanner in = new Scanner(System.in);
			if (listeLivre.size() > 0) {
				System.out
						.println("Souhaitez-vous reserver un livre ou modifier ses informations ? (Oui : o / Non : n)");
				String userSearchChoice = in.next();
				switch (userSearchChoice) {
				case "o":
					searchBookByTitle(listeLivre);
					break;
				case "n":
					System.out.println("Souhaitez-vous effectuer une nouvelle recherche ? (Oui : o / Non : n)");
					String userChoice = in.next();
					switch (userChoice) {
					case "o":
						searchBook();
						break;
					case "n":
						Menu.mainMenu();
						break;
					default:
						confirmSearchBook(listeLivre);
					}
					break;
				default:
					System.out.println("entrée invalide");
					confirmSearchBook(listeLivre);
				}

			} else {
				System.out.println(
						"Aucun livres de cet.te auteur.ice n'est présent, souhaitez vous effectuer une nouvelle recherche ? (Oui : o / Non : n)");
			}
			String userChoice = in.next();
			switch (userChoice) {
			case "o":
				searchBook();
				break;
			case "n":
				Menu.mainMenu();
				break;
			default:
				System.out.println("entrée invalide");
				confirmSearchBook(listeLivre);
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			confirmSearchBook(listeLivre);
		}
	}

	// search menu for book searching by title
	private static void searchBookByTitle(final List<Livre> listeLivre) {
		try {
			Scanner in = new Scanner(System.in);
			System.out.println(
					"Souhaitez vous gerer la location d'un livre ou modifer ses informations ? (louer/rendre : l / Modifier : m)");
			String userChoice = in.nextLine();
			boolean rent = false;
			switch (userChoice) {
			case "m":
				rent = false;
				break;
			case "l":
				rent = true;
				break;
			default:
				System.out.println("entrée invalide");
				searchBookByTitle(listeLivre);
			}
			System.out.println("Renseignez le titre du livre :");
			String searchTitle = in.nextLine().toLowerCase();
			searchTitle.toLowerCase();
			for (Livre livre : listeLivre) {
				if (!rent && searchTitle.equalsIgnoreCase(livre.getTitre())) {
					System.out.println("Vous avez selectionné" + " " + livre.getTitre());
					Livre selectedBook = livre;
					bookModifier(selectedBook);
				} else if (rent && searchTitle.equalsIgnoreCase(livre.getTitre())) {
					System.out.println("Vous avez selectionné" + " " + livre.getTitre());
					Livre selectedBook = livre;
					userRentChoice(selectedBook);
				} else {
					System.out.println("Aucun titre corespondant, merci de choisir dans cette liste :");
					for (Livre livres : listeLivre) {
						System.out.println(livres.showBookInfos());
					}
					searchBookByTitle(listeLivre);
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			searchBookByTitle(listeLivre);
		}
	}

	private static void userRentChoice(final Livre livre) {
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Souhaitez-vous louer ou rendre un livre ? (Louer : l / Rendre : r)");
			String userRentchoice = in.nextLine();
			switch (userRentchoice) {
			case "l":
				bookRenter(livre);
				break;
			case "r":
				bookTurnIn(livre);
				break;
			default:
				userRentChoice(livre);
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			userRentChoice(livre);
		}
	}

	// menu for book modifying
	private static void bookModifier(final Livre livre) {
		try {
			String oldTitle = livre.getTitre();
			Scanner in = new Scanner(System.in);
			System.out.println("Voici les informations actuelles du livre selectionné : ");
			System.out.println(livre.showBookInfos());
			System.out.println("______________________________");
			System.out.println("|                            |");
			System.out.println("|Vous souhaitez modifier :   |");
			System.out.println("|1 - le titre                |");
			System.out.println("|2 - l'auteur.ice            |");
			System.out.println("|3 - le genre                |");
			System.out.println("|4 - le nombre de pages      |");
			System.out.println("|5 - la référence            |");
			System.out.println("|6 - Retour au menu principal|");
			System.out.println("|____________________________|");
			int userChoice = in.nextInt();
			in.nextLine(); // Consume newline left-over to workaround a bug found at
							// https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
			switch (userChoice) {
			case 1:
				System.out.println("Inserez le nouveau titre :");
				String newTitle = in.nextLine().toLowerCase();
				livre.setTitre(newTitle);
				bookModifier(livre);
				break;
			case 2:
				System.out.println("Inserez le/a nouveau/elle auteur.ice :");
				String newAuthor = in.nextLine().toLowerCase();
				livre.setAuteur(newAuthor);
				bookModifier(livre);
				break;
			case 3:
				System.out.println("Inserez le nouveau genre :");
				String newGenre = in.nextLine().toLowerCase();
				livre.setGenre(newGenre);
				bookModifier(livre);
				break;
			case 4:
				System.out.println("Inserez le nouveau nombre de pages :");
				int newPageNumber = in.nextInt();
				livre.setPages(newPageNumber);
				bookModifier(livre);
				break;
			case 5:
				System.out.println("Inserez la nouvelle référence :");
				String newRef = in.nextLine().toLowerCase();
				livre.setRef(newRef);
				bookModifier(livre);
				break;
			case 6:
				EntityManager em = JPA.getEntityManager();
				em.getTransaction().begin();
				Query query = em.createNamedQuery("Livre.setChanges").setParameter("titre", livre.getTitre())
						.setParameter("auteur", livre.getAuteur()).setParameter("genre", livre.getGenre())
						.setParameter("pages", livre.getPages()).setParameter("ref", livre.getRef())
						.setParameter("oldTitle", oldTitle);
				query.executeUpdate();
				em.getTransaction().commit();
				Menu.mainMenu();
				break;
			default:
				System.out.println("entrée invalide");
				bookModifier(livre);
			}
			bookModifier(livre);
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			bookModifier(livre);
		}
	}

	// menu for book renting
	private static void bookRenter(final Livre livre) {
		try {
			if (livre.isDisponible()) {
				Scanner in = new Scanner(System.in);
				StringBuilder selected = new StringBuilder();
				selected.append("Vous avez selectionné : ").append(livre.getTitre()).append(" de : ")
						.append(livre.getAuteur());
				System.out.println(selected);
				System.out.println("");
				System.out.println(
						"Confirmez la durée sur laquelle vous souhaitez réserver le livre en jours (max : 30)");
				int rentDuration = in.nextInt();
				in.nextLine(); // consuming leftover "\n"
				if (rentDuration > 0 && rentDuration <= 30) {
					StringBuilder confirmRent = new StringBuilder();
					confirmRent.append("Confirmez vous vouloir réserver : ").append(livre.getTitre()).append(" pour ")
							.append(rentDuration).append(" jours ? (Oui : o / Non :n)");
					System.out.println(confirmRent);
					String userResponse = in.nextLine();
					switch (userResponse) {
					case "o":
						DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
						Calendar cal = Calendar.getInstance();
						Date dateDebut = cal.getTime();
						cal.add(Calendar.DATE, rentDuration);
						Date dateFin = cal.getTime();
						StringBuilder result = new StringBuilder();
						result.append("|Votre réservation est bien confirmée à partir du ")
								.append(dtf.format(dateDebut)).append(" au ").append(dtf.format(dateFin)).append("|");
						System.out
								.println("___________________________________________________________________________");
						System.out.println(result);
						System.out
								.println("|_________________________________________________________________________|");
						livre.setDisponible(false);
						DatesReserv res = new DatesReserv(dateDebut, dateFin, livre);
						EntityManager em = JPA.getEntityManager();
						em.getTransaction().begin();
						em.persist(res);
						em.getTransaction().commit();
						Menu.mainMenu();
						break;
					case "n":
						Menu.mainMenu();
						break;
					default:
						System.out.println("entrée invalide");
						bookRenter(livre);
					}
				} else {
					System.out.println("Nombre de jours invalide");
					bookRenter(livre);
				}
			} else {
				System.out.println("Désolé, le livre que vous souhaitez réserver est indisponible pour l'instant");
				Menu.mainMenu();
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			bookRenter(livre);
		}
	}

	// Menu for turning back books
	private static void bookTurnIn(final Livre livre) {
		int idlivre = livre.getIdlivre();
		EntityManager em = JPA.getEntityManager();
		Reservation reservation = (Reservation) (em
				.createQuery("SELECT r FROM Reserver r WHERE idlivre ='" + idlivre + "'").getSingleResult());
		if (idlivre == reservation.getIdlivre()) {
			JPA.getEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("DELETE FROM Reserver r WHERE r.idlivre = '" + idlivre + "'");
			query.executeUpdate();
			em.getTransaction().commit();
			System.out.println("_____________________________");
			System.out.println("|Le livre à bien été rendu !|");
			System.out.println("|___________________________|");
		} else {
			System.out.println("___________________________");
			System.out.println("|Ce livre n'est pas loué !|");
			System.out.println("|_________________________|");
		}
		Menu.mainMenu();
	}
}
