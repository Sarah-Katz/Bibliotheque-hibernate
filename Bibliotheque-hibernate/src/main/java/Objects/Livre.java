package Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This class represents a book and it's informations : Title, Author, Genre,
 * Number of pages and Reference number.
 * 
 * @author Sarah Katz
 *
 */
@Entity
@NamedQuery(name = "Livre.showAll", query = "SELECT l FROM Livre l ORDER BY l.ref")
@NamedQuery(name = "Livre.searchAuthor", query = "SELECT l FROM Livre l WHERE l.auteur LIKE :inputTitle")
@NamedQuery(name = "Livre.setChanges", query = "UPDATE Livre l SET titre = :titre, auteur = :auteur, genre = :genre, pages = :pages, ref = :ref WHERE titre = :oldTitle")
@Table(name = "livre")
public class Livre {
	@Column(name = "titre")
	private String titre;
	@Column(name = "auteur")
	private String auteur;
	@Column(name = "genre")
	private String genre;
	@Column(name = "pages")
	private int pages;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idlivre")
	private int idlivre;
	@Column(name = "disponible")
	private boolean disponible;
	@Column(name = "ref")
	private String ref;

	public Livre() {
	}

	/**
	 * * This constructor creates an instance of Book using parameters
	 *
	 * @param bookList   the list containing books to be used in Library class
	 * @param titre      Book title
	 * @param auteur     Book author
	 * @param genre      Book genre
	 * @param pages      Number of pages in the book
	 * @param disponible Availability of the book
	 * @param ref        Reference number of the book
	 */
	public Livre(String titre, String auteur, String genre, int pages, String ref) {
		this.titre = titre;
		this.auteur = auteur;
		this.genre = genre;
		this.pages = pages;
		this.disponible = true;
		this.ref = ref;
	}

	/**
	 * Gets and returns all infos about a book
	 * 
	 * @return the book's title, author, genre, page number and reference number
	 */
	public StringBuilder showBookInfos() {
		StringBuilder result = new StringBuilder();
		result.append("Titre : ").append(this.getTitre()).append("\n");
		result.append("Auteur.ice : ").append(this.getAuteur()).append("\n");
		result.append("Genre : ").append(this.getGenre()).append("\n");
		result.append("Nombre de pages : ").append(this.getPages()).append("\n");
		result.append("Référence : ").append(this.getRef()).append("\n");
		return result;
	}

	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * @return the auteur
	 */
	public String getAuteur() {
		return auteur;
	}

	/**
	 * @param auteur the auteur to set
	 */
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the pages
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(int pages) {
		this.pages = pages;
	}

	/**
	 * @return the idlivre
	 */
	public int getIdlivre() {
		return idlivre;
	}

	/**
	 * @param idlivre the idlivre to set
	 */
	public void setIdlivre(int idlivre) {
		this.idlivre = idlivre;
	}

	/**
	 * @return the disponible
	 */
	public boolean isDisponible() {
		return disponible;
	}

	/**
	 * @param disponible the disponible to set
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	/**
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

}
