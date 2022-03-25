package it.prova.gestionepermessi.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Sesso;

public class DipendenteDTO {

	private Long id;

	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	@NotBlank(message = "{codFis.notblank}")
	@Size(min = 16, max = 16, message = "Il valore inserito deve essere lungo esattamente {min} caratteri")
	private String codFis;

	private String email;

	@NotNull(message = "{dataNascita.notnull}")
	private Date dataNascita;

	@NotNull(message = "{dataAssunzione.notnull}")
	private Date dataAssunzione;

	private Date dataDimissioni;

	@NotNull(message = "{sesso.notblank}")
	private Sesso sesso;

	private Set<RichiestaPermesso> richiestePermesso = new HashSet<>();

	private Long ruoloId;

	public DipendenteDTO() {
		super();
	}

	public DipendenteDTO(Long id, String nome, String cognome, String codFis, String email, Date dataNascita,
			Date dataAssunzione, Date dataDimissioni, Sesso sesso, Set<RichiestaPermesso> richiestePermesso) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.dataDimissioni = dataDimissioni;
		this.sesso = sesso;
		this.richiestePermesso = richiestePermesso;
	}

	public DipendenteDTO(Long id, String nome, String cognome, String codFis, String email, Date dataNascita,
			Date dataAssunzione, Date dataDimissioni, Sesso sesso) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.dataDimissioni = dataDimissioni;
		this.sesso = sesso;
	}

	public DipendenteDTO(String nome, String cognome, String codFis, String email, Date dataNascita, Sesso sesso) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.sesso = sesso;
	}

	public DipendenteDTO(String nome, String cognome) {
		super();
		this.nome = nome;
		this.cognome = cognome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodFis() {
		return codFis;
	}

	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public Date getDataDimissioni() {
		return dataDimissioni;
	}

	public void setDataDimissioni(Date dataDimissioni) {
		this.dataDimissioni = dataDimissioni;
	}

	public Sesso getSesso() {
		return sesso;
	}

	public void setSesso(Sesso sesso) {
		this.sesso = sesso;
	}



	public Long getRuoloId() {
		return ruoloId;
	}

	public void setRuoloId(Long ruoloId) {
		this.ruoloId = ruoloId;
	}

	public Set<RichiestaPermesso> getRichiestePermesso() {
		return richiestePermesso;
	}

	public void setRichiestePermesso(Set<RichiestaPermesso> richiestePermesso) {
		this.richiestePermesso = richiestePermesso;
	}

	public static DipendenteDTO buildDipendenteDTOFromModel(Dipendente dipendenteModel) {
		DipendenteDTO result = new DipendenteDTO(dipendenteModel.getId(), dipendenteModel.getNome(),
				dipendenteModel.getCognome(), dipendenteModel.getCodFis(), dipendenteModel.getEmail(),
				dipendenteModel.getDataNascita(), dipendenteModel.getDataAssunzione(),
				dipendenteModel.getDataDimissioni(), dipendenteModel.getSesso());

		return result;
	}

	public static List<DipendenteDTO> createDipendenteDTOListFromModelList(List<Dipendente> dipendentiInput) {
		return dipendentiInput.stream().map(dipendenteEntity -> {
			return DipendenteDTO.buildDipendenteDTOFromModel(dipendenteEntity);
		}).collect(Collectors.toList());
	}

	public Dipendente buildDipendenteModel(boolean includeUtente) {
		Dipendente result = new Dipendente(this.id, this.nome, this.cognome, this.codFis, this.email, this.dataNascita,
				this.dataAssunzione, this.dataDimissioni, this.sesso);

		return result;
	}

	@Override
	public String toString() {
		return "DipendenteDTO [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", codFis=" + codFis + ", email="
				+ email + ", dataNascita=" + dataNascita + ", dataAssunzione=" + dataAssunzione + ", dataDimissioni="
				+ dataDimissioni + ", sesso=" + sesso + "]";
	}

}
