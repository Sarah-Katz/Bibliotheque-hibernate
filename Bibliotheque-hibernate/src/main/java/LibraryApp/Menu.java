package LibraryApp;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Sarah Katz
 *
 */
public class Menu {

	/**
	 * 
	 * 
	 */
	public static void startProgram() {
		mainMenu();
	}

	/**
	 * This method calls the main menu to be displayed and let's user decide what
	 * they want to do using numbers for action selection through a switch
	 *
	 * @param library
	 * @param bookList
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
			System.out.println("|4 -                                                    |");
			System.out.println("|5 -                                                    |");
			System.out.println("|-------------------------------------------------------|");
			System.out.println("|6 - Fermer le programme                                |");
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
				
				break;
			case 5:
				
				break;
			case 6:
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
