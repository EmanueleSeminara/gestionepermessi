package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.repository.RuoloRepository;

@Service
public class RuoloServiceImpl implements RuoloService {

	@Autowired
	private RuoloRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Ruolo> listAllElements() {
		return (List<Ruolo>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Ruolo caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Ruolo ruoloInstance) {
		repository.save(ruoloInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Ruolo ruoloInstance) {
		repository.save(ruoloInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Ruolo ruoloInstance) {
		repository.delete(ruoloInstance);
	}

	@Transactional(readOnly = true)
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) {
		return repository.findByDescrizioneAndCodice(descrizione, codice);
	}

	@Override
	public List<Ruolo> listAllElementsExceptBy(Long[] ruoliIds) {
		return repository.findByIdNotIn(ruoliIds);
	}

}
