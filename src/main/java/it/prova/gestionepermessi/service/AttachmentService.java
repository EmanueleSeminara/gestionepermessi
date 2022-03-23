package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Attachment;

public interface AttachmentService {
	public List<Attachment> listAllElements();

	public Attachment caricaSingoloElemento(Long id);

	public void aggiorna(Attachment attachmentInstance);

	public void inserisciNuovo(Attachment attachmentInstance);

	public void rimuovi(Attachment attachmentInstance);

}
