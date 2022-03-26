package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioService {
	public List<Messaggio> listAllElements();

	public Messaggio caricaSingoloElemento(Long id);

	public Messaggio caricaSingoloElementoEager(Long id);

	public void aggiorna(Messaggio messaggioInstance);

	public void inserisciNuovo(Messaggio messaggioInstance);

	public void rimuovi(Messaggio messaggioInstance);

	public Page<Messaggio> findByExample(Messaggio example, Integer pageNo, Integer pageSize, String sortBy);

	public int numeroMessaggiDaLeggere();
}
