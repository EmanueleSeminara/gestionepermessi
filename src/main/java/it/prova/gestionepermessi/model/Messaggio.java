package it.prova.gestionepermessi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private boolean letto = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utenteInserimento;

	public Messaggio() {
		super();
	}

	public Messaggio(String testo, String oggetto, boolean letto, Utente utenteInserimento) {
		super();
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.utenteInserimento = utenteInserimento;
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

	public boolean isLetto() {
		return letto;
	}

	public void setLetto(boolean letto) {
		this.letto = letto;
	}

	public Utente getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(Utente utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}

}
