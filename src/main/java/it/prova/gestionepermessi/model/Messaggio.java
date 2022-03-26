package it.prova.gestionepermessi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "messaggio")
public class Messaggio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "testo")
	private String testo;

	@Column(name = "oggetto")
	private String oggetto;

	@Column(name = "letto")
	private Boolean letto = false;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "richiestapermesso_id", referencedColumnName = "id", nullable = true)
	RichiestaPermesso richiestaPermesso;

	public Messaggio() {
		super();
	}

	public Messaggio(Boolean letto) {
		super();
		this.letto = letto;
	}

	public Messaggio(Long id) {
		super();
		this.id = id;
	}

	public Messaggio(String testo) {
		super();
		this.testo = testo;
	}

	public Messaggio(Long id, String testo, String oggetto, Boolean letto) {
		super();
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
	}

	public Messaggio(String testo, String oggetto, Boolean letto, RichiestaPermesso richiestaPermesso) {
		super();
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.richiestaPermesso = richiestaPermesso;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Boolean getLetto() {
		return letto;
	}

	public void setLetto(Boolean letto) {
		this.letto = letto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RichiestaPermesso getRichiestaPermesso() {
		return richiestaPermesso;
	}

	public void setRichiestaPermesso(RichiestaPermesso richiestaPermesso) {
		this.richiestaPermesso = richiestaPermesso;
	}

	@Override
	public String toString() {
		return "Messaggio [id=" + id + ", testo=" + testo + ", oggetto=" + oggetto + ", letto=" + letto + "]";
	}

}
