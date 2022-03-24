package it.prova.gestionepermessi.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.validation.ValidationNoPassword;
import it.prova.gestionepermessi.validation.ValidationWithPassword;

public class UtenteDTO {

	private Long id;

	@NotBlank(message = "{username.notblank}", groups = { ValidationWithPassword.class, ValidationNoPassword.class })
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;

	@NotBlank(message = "{password.notblank}", groups = ValidationWithPassword.class)
	@Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
	private String password;

	private String confermaPassword;

	private Date dateCreated;

	private StatoUtente stato;

	private DipendenteDTO dipendente;

	private Long[] ruoliIds;

	public UtenteDTO() {
	}

	public UtenteDTO(Long id, String username, Date dateCreated, StatoUtente stato) {
		super();
		this.id = id;
		this.username = username;
		this.dateCreated = dateCreated;
		this.stato = stato;
	}

	public UtenteDTO(Long id, String username, StatoUtente stato) {
		super();
		this.id = id;
		this.username = username;
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public Long[] getRuoliIds() {
		return ruoliIds;
	}

	public void setRuoliIds(Long[] ruoliIds) {
		this.ruoliIds = ruoliIds;
	}

	public DipendenteDTO getDipendente() {
		return dipendente;
	}

	public void setDipendente(DipendenteDTO dipendente) {
		this.dipendente = dipendente;
	}

	public Utente buildUtenteModel(boolean includeIdRoles, boolean includeDipendente) {
		Utente result = new Utente(this.id, this.username, this.password, this.dateCreated, this.stato);
		if (includeIdRoles && ruoliIds != null)
			result.setRuoli(Arrays.asList(ruoliIds).stream().map(id -> new Ruolo(id)).collect(Collectors.toSet()));

		if (includeDipendente && this.dipendente != null) {
			result.setDipendente(new Dipendente(this.dipendente.getNome(), this.dipendente.getCognome()));
		}

		return result;
	}

	public static UtenteDTO buildUtenteDTOFromModel(Utente utenteModel) {
		UtenteDTO result = new UtenteDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getDateCreated(),
				utenteModel.getStato());

		if (!utenteModel.getRuoli().isEmpty())
			result.ruoliIds = utenteModel.getRuoli().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});

		return result;
	}

	@Override
	public String toString() {
		return "UtenteDTO [id=" + id + ", username=" + username + ", password=" + password + ", confermaPassword="
				+ confermaPassword + ", dateCreated=" + dateCreated + ", stato=" + stato + "]";
	}

}
