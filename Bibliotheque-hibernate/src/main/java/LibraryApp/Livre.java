package LibraryApp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.bytebuddy.build.ToStringPlugin.Exclude;

/**
 * @author Sarah Katz
 *
 */
@Entity
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
	
	/**
	 * @param titre
	 * @param auteur
	 * @param genre
	 * @param pages
	 * @param idlivre
	 * @param disponible
	 * @param ref
	 */
	public Livre(String titre, String auteur, String genre, int pages, int idlivre, boolean disponible, String ref) {
		this.titre = titre;
		this.auteur = auteur;
		this.genre = genre;
		this.pages = pages;
		this.idlivre = idlivre;
		this.disponible = disponible;
		this.ref = ref;
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
