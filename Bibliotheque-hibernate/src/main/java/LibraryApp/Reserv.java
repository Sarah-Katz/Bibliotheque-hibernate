package LibraryApp;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Sarah Katz
 *
 */
@Entity
@Table(name = "reserv")
public class Reserv {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idres")
	private int idres;
	@Column(name = "debut")
	private Date debut;
	@Column(name = "fin")
	private Date fin;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "reserver", joinColumns = @JoinColumn(name = "idres"), inverseJoinColumns = @JoinColumn(name = "idlivre"))
	private Livre livre;

	public Reserv() {
	}

	/**
	 * @param debut
	 * @param fin
	 */
	public Reserv(Date debut, Date fin) {
		this.debut = debut;
		this.fin = fin;
	}

	/**
	 * @return the idres
	 */
	public int getIdres() {
		return idres;
	}

	/**
	 * @param idres the idres to set
	 */
	public void setIdres(int idres) {
		this.idres = idres;
	}

	/**
	 * @return the debut
	 */
	public Date getDebut() {
		return debut;
	}

	/**
	 * @param debut the debut to set
	 */
	public void setDebut(Date debut) {
		this.debut = debut;
	}

	/**
	 * @return the fin
	 */
	public Date getFin() {
		return fin;
	}

	/**
	 * @param fin the fin to set
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}

}
