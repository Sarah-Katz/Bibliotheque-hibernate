package Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Sarah Katz
 *
 */
@Entity
@NamedQuery(name = "Reserver.listRes", query = "SELECT r FROM Reservation r")
@Table(name = "reserver")
public class Reservation {
	@Column(name  = "idlivre")
	private int idlivre;
	@Id
	@Column(name = "idres")
	private int idres;

	public Reservation() {

	}

	public int getIdlivre() {
		return idlivre;
	}

	public void setIdlivre(int idlivre) {
		this.idlivre = idlivre;
	}

	public int getIdres() {
		return idres;
	}

	public void setIdres(int idres) {
		this.idres = idres;
	}


}
