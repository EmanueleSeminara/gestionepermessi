package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteService {
	public List<Dipendente> listAllElements();

	public Dipendente caricaSingoloElemento(Long id);

	public Dipendente caricaSingoloElementoEager(Long id);

	public void aggiorna(Dipendente dipendenteInstance);

	public void inserisciDipendenteEUtenteConRuoli(Dipendente dipendenteInstance, Long ruoloId);

	public void rimuovi(Dipendente dipendenteInstance);

	public Page<Dipendente> findByExample(Dipendente example, Integer pageNo, Integer pageSize, String sortBy);
}
