package it.prova.gestionepermessi.dto;

import java.util.List;
import java.util.stream.Collectors;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public class MessaggioDTO {

	private Long id;

	private String testo;

	private String oggetto;

	private Boolean letto = false;

	RichiestaPermesso richiestaPermesso;

	public MessaggioDTO() {
		super();
	}

	public MessaggioDTO(String testo, String oggetto, boolean letto, RichiestaPermesso richiestaPermesso) {
		super();
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.richiestaPermesso = richiestaPermesso;
	}

	public MessaggioDTO(Long id, String testo, String oggetto, boolean letto, RichiestaPermesso richiestaPermesso) {
		super();
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.richiestaPermesso = richiestaPermesso;
	}

	public MessaggioDTO(Long id, String testo, String oggetto, boolean letto) {
		super();
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
	}

	public MessaggioDTO(String testo, String oggetto, boolean letto) {
		super();
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public RichiestaPermesso getRichiestaPermesso() {
		return richiestaPermesso;
	}

	public void setRichiestaPermesso(RichiestaPermesso richiestaPermesso) {
		this.richiestaPermesso = richiestaPermesso;
	}

	public static MessaggioDTO buildMessaggioDTOFromModel(Messaggio messaggioModel) {
		MessaggioDTO result = new MessaggioDTO(messaggioModel.getId(), messaggioModel.getTesto(),
				messaggioModel.getOggetto(), messaggioModel.getLetto(), messaggioModel.getRichiestaPermesso());

		return result;
	}

	public static List<MessaggioDTO> createMessaggioDTOListFromModelList(List<Messaggio> richiestePermessoInput) {
		return richiestePermessoInput.stream().map(messaggioEntity -> {
			return MessaggioDTO.buildMessaggioDTOFromModel(messaggioEntity);
		}).collect(Collectors.toList());
	}

	public Messaggio buildMessaggioModel(boolean includeRichiestaPermesso) {
		Messaggio result = new Messaggio(this.id, this.testo, this.oggetto, this.letto);

		if (includeRichiestaPermesso && this.richiestaPermesso != null) {
			result.setRichiestaPermesso(this.richiestaPermesso);
		}

		return result;
	}

	@Override
	public String toString() {
		return "MessaggioDTO [id=" + id + ", testo=" + testo + ", oggetto=" + oggetto + ", letto=" + letto + "]";
	}

}
