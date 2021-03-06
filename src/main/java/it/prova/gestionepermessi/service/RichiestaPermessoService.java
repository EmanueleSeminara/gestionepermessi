package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {
	public List<RichiestaPermesso> listAllElements();

	public List<RichiestaPermesso> listAllElementsByUsername(String usernameInput);

	public RichiestaPermesso caricaSingoloElemento(Long id);

	public RichiestaPermesso caricaSingoloElementoEager(Long id);

	public void aggiorna(RichiestaPermesso richiestaPermessoInstance);

	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance);

	public void rimuovi(Long idRichiestaPermesso);

	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize,
			String sortBy);

	public void changeRichiestaPermessoStato(Long richiestaPermessoInstanceId);

	public void inserisciNuovaRichiestaPermessoECreaMessaggio(RichiestaPermesso richiestaPermessoInstance,
			String usernameInput);
}