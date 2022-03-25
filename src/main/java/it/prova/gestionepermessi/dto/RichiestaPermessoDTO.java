package it.prova.gestionepermessi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.TipoPermesso;

public class RichiestaPermessoDTO {

	private Long id;

	private TipoPermesso tipoPermesso;

	private Date dataInizio;

	private Date dataFine;

	private Boolean approvato = false;

	private String codiceCertificato;

	private String note;

	private Attachment attachment;

	private Dipendente dipendente;

	public RichiestaPermessoDTO() {
		super();
	}

	public RichiestaPermessoDTO(Long id, TipoPermesso tipoPermesso, Date dataInizio, Date dataFine, Boolean approvato,
			String codiceCertificato, String note, Attachment attachment, Dipendente dipendente) {
		super();
		this.id = id;
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
		this.attachment = attachment;
		this.dipendente = dipendente;
	}

	public RichiestaPermessoDTO(Long id, TipoPermesso tipoPermesso, Date dataInizio, Date dataFine, Boolean approvato,
			String codiceCertificato, String note) {
		super();
		this.id = id;
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPermesso getTipoPermesso() {
		return tipoPermesso;
	}

	public void setTipoPermesso(TipoPermesso tipoPermesso) {
		this.tipoPermesso = tipoPermesso;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Boolean getApprovato() {
		return approvato;
	}

	public void setApprovato(Boolean approvato) {
		this.approvato = approvato;
	}

	public String getCodiceCertificato() {
		return codiceCertificato;
	}

	public void setCodiceCertificato(String codiceCertificato) {
		this.codiceCertificato = codiceCertificato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}

	public static RichiestaPermessoDTO buildRichiestaPermessoDTOFromModel(RichiestaPermesso richiestaPermessoModel) {
		RichiestaPermessoDTO result = new RichiestaPermessoDTO(richiestaPermessoModel.getId(),
				richiestaPermessoModel.getTipoPermesso(), richiestaPermessoModel.getDataInizio(),
				richiestaPermessoModel.getDataFine(), richiestaPermessoModel.isApprovato(),
				richiestaPermessoModel.getCodiceCertificato(), richiestaPermessoModel.getNote(),
				richiestaPermessoModel.getAttachment(), richiestaPermessoModel.getDipendente());

		return result;
	}

	public static List<RichiestaPermessoDTO> createRichiestaPermessoDTOListFromModelList(
			List<RichiestaPermesso> richiestePermessoInput) {
		return richiestePermessoInput.stream().map(richiestaPermessoEntity -> {
			return RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaPermessoEntity);
		}).collect(Collectors.toList());
	}

	public RichiestaPermesso buildRichiestaPermessoModel(boolean includeDipendente) {
		RichiestaPermesso result = new RichiestaPermesso(this.id, this.tipoPermesso, this.dataInizio, this.dataFine,
				this.approvato, this.codiceCertificato, this.note, this.attachment);

		if (includeDipendente) {
			result.setDipendente(this.dipendente);
		}

		return result;
	}
}
