package LibraryApp;

import java.util.InputMismatchException;
import java.util.Scanner;

import Utils.JPA;

/**
 * The Menu class provides a main menu for the program. It allows the user to
 * select different actions such as registering a new book, displaying the list
 * of books, reserving or returning a book, or closing the program. The menu is
 * displayed to the user in console and the user can make a selection by
 * entering the corresponding number.
 * 
 * @author Sarah Katz
 *
 */
public class Menu {

	/**
	 * Displays the main menu and prompts the user to select an action. The user can
	 * choose from the following actions:
	 * <ul>
	 * <li>1 - Register a new book</li>
	 * <li>2 - Display the list of books</li>
	 * <li>3 - Reserve, return, or modify a book</li>
	 * <li>4 - Close the program</li>
	 * </ul>
	 * 
	 * @throws InputMismatchException if the user enters an invalid input
	 *
	 */
	protected static void mainMenu() {
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("_________________________________________________________");
			System.out.println("|                                                       |");
			System.out.println("|Bienvenue dans le menu de navigation, vous souhaitez : |");
			System.out.println("|1 - Enregistrer un nouveau livre                       |");
			System.out.println("|2 - Afficher la liste des livres                       |");
			System.out.println("|3 - Réserver, rendre ou modifier un livre              |");
			System.out.println("|-------------------------------------------------------|");
			System.out.println("|4 - Fermer le programme                                |");
			System.out.println("|_______________________________________________________|");
			int userChoice = in.nextInt();
			switch (userChoice) {
			case 1:
				BookManager.newBook();
				break;
			case 2:
				BookManager.showBookList();
				break;
			case 3:
				BookManager.searchBook();
				break;
			case 4:
				in.close();
				JPA.shutdownEntityManager();
				JPA.shutdownFactory();
				System.exit(0);
				break;
			default:
				System.out.println("/!\\ Merci de renseigner le chiffre de l'action souhaitée /!\\");
				mainMenu();
			}
		} catch (InputMismatchException e) {
			System.out.println("/!\\ Merci de renseigner le chiffre de l'action souhaitée /!\\");
			mainMenu();
		}
	}
}
