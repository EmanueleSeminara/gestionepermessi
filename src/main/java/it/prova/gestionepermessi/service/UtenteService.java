package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Utente;

public interface UtenteService {
	public List<Utente> listAllElements();

	public Utente caricaSingoloElemento(Long id);

	public Utente caricaSingoloElementoEager(Long id);

	public void aggiorna(Utente utenteInstance);

	public void inserisciNuovo(Utente utenteInstance);

	public void rimuovi(Utente utenteInstance);

	public Page<Utente> findByExample(Utente example, Integer pageNo, Integer pageSize, String sortBy);
}
