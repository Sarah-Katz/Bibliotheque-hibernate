package LibraryApp;

import java.awt.print.Book;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
			String title = in.nextLine();
			switch (title) {
			case "m":
				Menu.mainMenu();
			default:
				System.out.println("L'auteur.ice du livre :");
				String author = in.nextLine();
				System.out.println("Le genre du livre :");
				String genre = in.nextLine();
				try {
					System.out.println("Le nombre de page :");
					pageNumber = in.nextInt();
					in.nextLine();
					System.out.println("La reference du livre :");
					ref = in.nextLine();
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
						.append("\n").append("Nombre de copies : ").append(ref);
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
			String searchedAuthor = in.nextLine();
			searchedAuthor.toLowerCase();
			switch (searchedAuthor) {
			case "m":
				Menu.mainMenu();
			default:
				// TODO book found with this author
				EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
				@SuppressWarnings("unchecked")
				List<Livre> livre = (List<Livre>) em.createNamedQuery("Livre.searchAuthor").getResultList();
				livre.setParameter("code", "%" + searchedAuthor + "%");
				StringBuilder result = new StringBuilder();
				if (livre.size() > 0) {
					for (Livre l : livre) {
						result.setLength(0);
						result.append("Titre : ").append(l.getTitre()).append(", Auteur.ice : ").append(l.getAuteur())
								.append(", Genre : ").append(l.getGenre()).append(", Nomres de pages : ").append(l.getPages())
								.append(", Disponible : ");
						if (l.isDisponible()) {
							result.append("oui.");
						} else {
							result.append("non");
						}
						System.out.println(result);
					}
				} else {
					//TODO : no match
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

		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		@SuppressWarnings("unchecked")
		List<Livre> livre = (List<Livre>) em.createNamedQuery("Livre.showAll").getResultList();
		StringBuilder result = new StringBuilder();
		for (Livre l : livre) {
			result.setLength(0);
			result.append("Titre : ").append(l.getTitre()).append(", Auteur.ice : ").append(l.getAuteur())
					.append(", Genre : ").append(l.getGenre()).append(", Nomres de pages : ").append(l.getPages())
					.append(", Disponible : ");
			if (l.isDisponible()) {
				result.append("oui.");
			} else {
				result.append("non");
			}
			System.out.println(result);
		}
		em.close();
		JPAUtil.shutdown();
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
				Livre livre = new Livre(title, author, genre, pageNumber, ref);
				// TODO add book to DB
				EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
				em.getTransaction().begin();
				em.persist(livre);
				em.getTransaction().commit();
				em.close();
				JPAUtil.shutdown();
				System.out.println("Votre livre à bien été enregistré.");
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
	private static void confirmSearchBook() {
		try {
			Scanner in = new Scanner(System.in);
			if (isFoundResult) {
				System.out
						.println("Souhaitez-vous reserver un livre ou modifier ses informations ? (Oui : o / Non : n)");
				String userSearchChoice = in.next();
				switch (userSearchChoice) {
				case "o":
					searchBookByTitle();
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
						confirmSearchBook();
					}
					break;
				default:
					System.out.println("entrée invalide");
					confirmSearchBook();
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
				confirmSearchBook();
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			confirmSearchBook();
		}
	}

	// search menu for book searching by title
	private static void searchBookByTitle() {
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
				searchBookByTitle();
			}
			System.out.println("Renseignez le titre du livre :");
			String searchTitle = in.nextLine();
			searchTitle.toLowerCase();
			for (Book book : bookOfSearchedAuthor) {
				if (!rent && searchTitle.equalsIgnoreCase(book.getTitle())) {
					System.out.println("Vous avez selectionné" + " " + book.getTitle());
					Book selectedBook = book;
					bookModifier(library, bookList, selectedBook);
				} else if (rent && searchTitle.equalsIgnoreCase(book.getTitle())) {
					System.out.println("Vous avez selectionné" + " " + book.getTitle());
					Book selectedBook = book;
					userRentChoice(library, bookList, selectedBook);
				} else {
					System.out.println("Aucun titre corespondant, merci de choisir dans cette liste :");
					for (Book books : bookOfSearchedAuthor) {
						System.out.println(books.showBookInfos());
					}
					searchBookByTitle(library, bookList, bookOfSearchedAuthor);
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			searchBookByTitle(library, bookList, bookOfSearchedAuthor);
		}
	}

	private static void userRentChoice(final Library library, final List<Book> bookList, final Book selectedBook) {
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Souhaitez-vous louer ou rendre un livre ? (Louer : l / Rendre : r)");
			String userRentchoice = in.nextLine();
			switch (userRentchoice) {
			case "l":
				bookRenter(library, bookList, selectedBook);
				break;
			case "r":
				bookTurnIn(library, bookList, selectedBook);
				break;
			default:
				userRentChoice(library, bookList, selectedBook);
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			userRentChoice(library, bookList, selectedBook);
		}
	}

	// menu for book modifying
	private static void bookModifier(final Library library, final List<Book> bookList, final Book book) {
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("______________________________");
			System.out.println("|                            |");
			System.out.println("|Vous souhaitez modifier :   |");
			System.out.println("|1 - le titre                |");
			System.out.println("|2 - l'auteur.ice            |");
			System.out.println("|3 - le genre                |");
			System.out.println("|4 - le nombre de pages      |");
			System.out.println("|5 - le nombre de copies     |");
			System.out.println("|6 - Retour au menu principal|");
			System.out.println("|____________________________|");
			int userChoice = in.nextInt();
			in.nextLine(); // Consume newline left-over to workaround a bug found at
							// https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
			switch (userChoice) {
			case 1:
				System.out.println("Inserez le nouveau titre :");
				String newTitle = in.nextLine();
				book.setTitle(newTitle);
				break;
			case 2:
				System.out.println("Inserez le/a nouveau/elle auteur.ice :");
				String newAuthor = in.nextLine();
				book.setAuthor(newAuthor);
				bookModifier(library, bookList, book);
				break;
			case 3:
				System.out.println("Inserez le nouveau genre :");
				String newGenre = in.nextLine();
				book.setGenre(newGenre);
				bookModifier(library, bookList, book);
				break;
			case 4:
				System.out.println("Inserez le nouveau nombre de pages :");
				int newPageNumber = in.nextInt();
				book.setPageNumber(newPageNumber);
				bookModifier(library, bookList, book);
				break;
			case 5:
				System.out.println("Inserez le nouveau nombre de copies :");
				int newCopies = in.nextInt();
				book.setCopies(newCopies);
				bookModifier(library, bookList, book);
				break;
			case 6:
				Menu.mainMenu(library, bookList);
				break;
			default:
				System.out.println("entrée invalide");
				bookModifier(library, bookList, book);
			}
			bookModifier(library, bookList, book);
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			bookModifier(library, bookList, book);
		}
	}

	// menu for book renting
	private static void bookRenter() {
		try {
			if (book.getCopies() == -1) {
				System.out.println(
						"Une erreur sur le nombre d'exemplaire est présente, merci de vérifier le fichier contenant la liste des livre");
				Menu.mainMenu(library, bookList);
			} else if (book.getCopies() <= 0) {
				System.out.println("Désolé, le livre que vous souhaitez réserver est indisponible pour l'instant");
				Menu.mainMenu(library, bookList);
			} else {
				Scanner in = new Scanner(System.in);
				StringBuilder selected = new StringBuilder();
				selected.append("Vous avez selectionné : ").append(book.getTitle()).append(" de : ")
						.append(book.getAuthor());
				System.out.println(selected);
				System.out.println("");
				System.out.println(
						"Confirmez la durée sur laquelle vous souhaitez réserver le livre en jours (max : 30)");
				int rentDuration = in.nextInt();
				in.nextLine(); // consuming leftover "\n"
				if (rentDuration > 0 && rentDuration <= 30) {
					StringBuilder confirmRent = new StringBuilder();
					confirmRent.append("Confirmez vous vouloir réserver : ").append(book.getTitle()).append(" pour ")
							.append(rentDuration).append(" jours ? (Oui : o / Non :n)");
					System.out.println(confirmRent);
					String userResponse = in.nextLine();
					switch (userResponse) {
					case "o":
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						LocalDateTime date = LocalDateTime.now();
						long endRentDateMilli = System.currentTimeMillis();
						endRentDateMilli = endRentDateMilli + (rentDuration * 86400000);
						LocalDateTime endRentDate = Instant.ofEpochMilli(endRentDateMilli)
								.atZone(ZoneId.systemDefault()).toLocalDateTime();
						book.setCopies(book.getCopies() - 1);
						StringBuilder result = new StringBuilder();
						result.append("|Votre réservation est bien confirmée à partir du ").append(dtf.format(date))
								.append(" au ").append(dtf.format(endRentDate)).append("|");
						System.out
								.println("___________________________________________________________________________");
						System.out.println(result);
						System.out
								.println("|_________________________________________________________________________|");
						Menu.mainMenu();
						break;
					case "n":
						Menu.mainMenu();
						break;
					default:
						System.out.println("entrée invalide");
						bookRenter();
					}
				} else {
					bookRenter();
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("___________________________________________________________");
			System.out.println("|Une erreur relative à l'entrée utilisateur s'est produite|");
			System.out.println("|_________________________________________________________|");
			bookRenter();
		}
	}

	// Menu for turning back books
	private static void bookTurnIn(final Library library, final List<Book> bookList, final Book book) {
		book.setCopies(book.getCopies() + 1);
		System.out.println("_____________________________");
		System.out.println("|Le livre à bien été rendu !|");
		System.out.println("|___________________________|");
		Menu.mainMenu(library, bookList);
	}
}
