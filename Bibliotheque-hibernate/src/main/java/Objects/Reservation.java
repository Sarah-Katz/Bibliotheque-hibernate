package Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * <ul>
 * <li>The Reservation class represents a reservation of a book in the library.
 * It includes information about the unique identifiers of both the book and the
 * reservation.</li>
 * <li>The Reservation class is annotated with the @Entity annotation,
 * indicating that it is a JPA entity. It is also annotated with the @NamedQuery
 * annotation, which defines a named query that can be used to retrieve
 * Reservation objects from the database.</li>
 * <li>The @Table annotation specifies the name of the database table that will
 * be used to store Reservation objects. The class has two fields, each of which
 * is annotated with the @Column annotation to specify the corresponding column
 * in the database table.</li>
 * <li>The idres field is annotated with the @Id annotation to indicate that it
 * is the primary key. The idlivre field represents the unique identifier of the
 * book associated with the reservation.</li>
 * </ul>
 * 
 * @author Sarah Katz
 *
 */
@Entity
@NamedQuery(name = "Reserver.listRes", query = "SELECT r FROM Reservation r")
@Table(name = "reserver")
public class Reservation {
	@Column(name = "idlivre")
	private int idlivre;
	@Id
	@Column(name = "idres")
	private int idres;

	public Reservation() {

	}

	/**
	 * @return the book id
	 */
	public int getIdlivre() {
		return idlivre;
	}

	/**
	 * @param idlivre the book id to set
	 */
	public void setIdlivre(int idlivre) {
		this.idlivre = idlivre;
	}

	/**
	 * @return the reservation id
	 */
	public int getIdres() {
		return idres;
	}

	/**
	 * @param idres the reservation id to set
	 */
	public void setIdres(int idres) {
		this.idres = idres;
	}

	
}
