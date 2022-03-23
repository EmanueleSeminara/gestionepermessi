package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Ruolo;

public interface RuoloService {
	public List<Ruolo> listAllElements();

	public Ruolo caricaSingoloElemento(Long id);

	public void aggiorna(Ruolo ruoloInstance);

	public void inserisciNuovo(Ruolo ruoloInstance);

	public void rimuovi(Ruolo ruoloInstance);

}