package it.prova.gestionepermessi.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.validation.ValidationNoPassword;
import it.prova.gestionepermessi.validation.ValidationWithPassword;

public class UtenteToShowDTO {

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

	private Set<RuoloDTO> ruoli;

	public UtenteToShowDTO() {
	}

	public UtenteToShowDTO(Long id, String username, Date dateCreated, StatoUtente stato) {
		super();
		this.id = id;
		this.username = username;
		this.dateCreated = dateCreated;
		this.stato = stato;
	}

	public UtenteToShowDTO(Long id, String username, StatoUtente stato) {
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

	public Set<RuoloDTO> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<RuoloDTO> ruoli) {
		this.ruoli = ruoli;
	}

	public Utente buildUtenteModel(boolean includeIdRoles) {
		Utente result = new Utente(this.id, this.username, this.dateCreated, this.stato);
		if (includeIdRoles && ruoli != null)
			result.setRuoli(ruoli.stream().map(ruoloEntity -> {
				return new Ruolo(ruoloEntity.getId(), ruoloEntity.getDescrizione(), ruoloEntity.getCodice());
			}).collect(Collectors.toSet()));

		return result;
	}

	// niente password...
	public static UtenteToShowDTO buildUtenteToShowDTOFromModel(Utente utenteModel) {
		UtenteToShowDTO result = new UtenteToShowDTO(utenteModel.getId(), utenteModel.getUsername(),
				utenteModel.getDateCreated(), utenteModel.getStato());

		if (!utenteModel.getRuoli().isEmpty())
			result.ruoli = RuoloDTO.createRuoloDTOListFromModelSet(utenteModel.getRuoli()).stream()
					.collect(Collectors.toSet());

		return result;
	}

	public static List<UtenteToShowDTO> createUtenteToShowDTOListFromModelList(List<Utente> utentiInput) {
		return utentiInput.stream().map(utenteToShowEntity -> {
			return UtenteToShowDTO.buildUtenteToShowDTOFromModel(utenteToShowEntity);
		}).collect(Collectors.toList());
	}

	public boolean isAdmin() {
		for (RuoloDTO ruoloItem : ruoli) {
			if (ruoloItem.getCodice().equals(Ruolo.ROLE_ADMIN))
				return true;
		}
		return false;
	}

	public boolean isAttivo() {
		return this.stato != null && this.stato.equals(StatoUtente.ATTIVO);
	}

	public boolean isDisabilitato() {
		return this.stato != null && this.stato.equals(StatoUtente.DISABILITATO);
	}

}
