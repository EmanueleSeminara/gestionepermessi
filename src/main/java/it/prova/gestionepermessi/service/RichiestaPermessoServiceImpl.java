package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.exceptions.RichiestaPermessoConDataInizioSuperataException;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.MessaggioRepository;
import it.prova.gestionepermessi.repository.RichiestaPermessoRepository;

@Service
public class RichiestaPermessoServiceImpl implements RichiestaPermessoService {

	@Autowired
	private RichiestaPermessoRepository repository;

	@Autowired
	private MessaggioRepository messaggioRepository;

	@Autowired
	private DipendenteService dipendenteService;

	@Autowired
	private UtenteService utenteService;

	@Override
	@Transactional(readOnly = true)
	public List<RichiestaPermesso> listAllElements() {
		return (List<RichiestaPermesso>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<RichiestaPermesso> listAllElementsByUsername(String usernameInput) {
		return (List<RichiestaPermesso>) repository.findByUsername(usernameInput);
	}

	@Override
	@Transactional(readOnly = true)
	public RichiestaPermesso caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(RichiestaPermesso richiestaPermessoInstance) {
		if (richiestaPermessoInstance == null || richiestaPermessoInstance.getId() == null) {
			throw new NullPointerException("La richiesta non è valida");
		}
		RichiestaPermesso richiestaPermessoReloaded = repository.findById(richiestaPermessoInstance.getId())
				.orElse(null);
		if (richiestaPermessoReloaded == null || richiestaPermessoReloaded.getId() == null
				|| richiestaPermessoReloaded.isApprovato()
				|| richiestaPermessoReloaded.getDataInizio().before(new Date())) {
			throw new IllegalArgumentException("La richiesta non può essere aggiornata");
		}

		richiestaPermessoReloaded.setAttachment(richiestaPermessoInstance.getAttachment());
		richiestaPermessoReloaded.setCodiceCertificato(richiestaPermessoInstance.getCodiceCertificato());
		richiestaPermessoReloaded.setDataFine(richiestaPermessoInstance.getDataFine());
		richiestaPermessoReloaded.setDataInizio(richiestaPermessoInstance.getDataInizio());
		richiestaPermessoReloaded.setNote(richiestaPermessoInstance.getNote());
		richiestaPermessoReloaded.setTipoPermesso(richiestaPermessoInstance.getTipoPermesso());

//		repository.save(richiestaPermessoInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance) {
		repository.save(richiestaPermessoInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idRichiestaPermesso) {
		System.out.println("ID RICHIESTA: " + idRichiestaPermesso);
		RichiestaPermesso richiestaPermessoReloaded = repository.findByIdEager(idRichiestaPermesso).orElse(null);
		System.out.println(richiestaPermessoReloaded);
		if (richiestaPermessoReloaded == null || richiestaPermessoReloaded.getId() == null
				|| richiestaPermessoReloaded.isApprovato() || richiestaPermessoReloaded.getDataInizio() == null
				|| richiestaPermessoReloaded.getDataInizio().before(new Date())) {
			throw new RichiestaPermessoConDataInizioSuperataException("Impossibile rimuovere la richiesta");
		}
		messaggioRepository.delete(messaggioRepository.findByRichiestaPermesso_id(richiestaPermessoReloaded.getId()));
		repository.delete(richiestaPermessoReloaded);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize,
			String sortBy) {
		Specification<RichiestaPermesso> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (example.getTipoPermesso() != null)
				predicates.add(cb.equal(root.get("tipoPermesso"), example.getTipoPermesso()));

			if (example.getDataFine() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataFine"), example.getDataFine()));

			if (example.getDataInizio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataInizio"), example.getDataInizio()));
			if (example.isApprovato() != null)
				predicates.add(cb.equal(root.get("approvato"), example.isApprovato()));

			if (StringUtils.isNotEmpty(example.getCodiceCertificato()))
				predicates.add(cb.like(cb.upper(root.get("codiceCertificato")),
						"%" + example.getCodiceCertificato().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getNote()))
				predicates.add(cb.like(cb.upper(root.get("note")), "%" + example.getNote().toUpperCase() + "%"));

			if (example.getDipendente() != null && example.getDipendente().getId() != null)
				predicates.add(cb.equal(root.get("dipendente"), example.getDipendente().getId()));

			root.fetch("dipendente", JoinType.LEFT);

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return repository.findAll(specificationCriteria, paging);
	}

	@Override
	public RichiestaPermesso caricaSingoloElementoEager(Long id) {
		return repository.findByIdEager(id).orElse(null);
	}

	@Override
	@Transactional
	public void changeRichiestaPermessoStato(Long richiestaPermessoInstanceId) {
		RichiestaPermesso richiestaPermessoInstance = caricaSingoloElemento(richiestaPermessoInstanceId);
		if (richiestaPermessoInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (richiestaPermessoInstance.getDataInizio().before(new Date())) {
			throw new RichiestaPermessoConDataInizioSuperataException(
					"Impossibile modificare lo stato della richiesta");
		}

		if (richiestaPermessoInstance.isApprovato()) {
			richiestaPermessoInstance.setApprovato(false);
		} else {
			richiestaPermessoInstance.setApprovato(true);
		}

	}

	@Override
	@Transactional
	public void inserisciNuovaRichiestaPermessoECreaMessaggio(RichiestaPermesso richiestaPermessoInstance,
			String usernameInput) {
		Utente utenteRichiesta = utenteService.findByUsername(usernameInput);
		if (utenteRichiesta == null) {
			System.out.println("PROBLEMA");
		}

		richiestaPermessoInstance.setDipendente(utenteRichiesta.getDipendente());
		repository.save(richiestaPermessoInstance);

		System.out.println(utenteRichiesta);
		System.out.println(utenteRichiesta.getDipendente());
		System.out.println(utenteRichiesta.getDipendente().getId() == null);
		System.out.println(richiestaPermessoInstance.getId() == null);
		if (utenteRichiesta.getDipendente().getId() == null || richiestaPermessoInstance.getId() == null) {
			throw new IllegalArgumentException("Errore");
		}
		repository.save(richiestaPermessoInstance);
		Messaggio nuovoMessaggio = new Messaggio();
		nuovoMessaggio.setOggetto("Richiesta permesso da parte di " + utenteRichiesta.getDipendente().getNome() + " "
				+ utenteRichiesta.getDipendente().getCognome());
		String testoMessaggio = "";

		testoMessaggio = "Il dipendente " + utenteRichiesta.getDipendente().getNome() + " ha richiesto un permesso per "
				+ richiestaPermessoInstance.getTipoPermesso();

		if (richiestaPermessoInstance.getDataFine() != null) {
			testoMessaggio += " nei giorni " + richiestaPermessoInstance.getDataInizio() + " "
					+ richiestaPermessoInstance.getDataFine() + "\n";
		} else {
			testoMessaggio += " nel giorno " + richiestaPermessoInstance.getDataInizio();
		}

		if (richiestaPermessoInstance.getAttachment() != null) {
			testoMessaggio += "Nella richiesta è statto allegato un file disponibile per il download: "; // inserire
																											// informazioni
																											// quando si
																											// capirà
																											// ola
																											// gestione
																											// file
		}
		if (richiestaPermessoInstance.getCodiceCertificato() != null) {
			testoMessaggio += " Il codice del certificato è: " + richiestaPermessoInstance.getCodiceCertificato();
		}
		if (richiestaPermessoInstance.getNote() != null) {
			testoMessaggio += "Nella richiesta sono presenti le seguenti note: " + richiestaPermessoInstance.getNote();
		}

		nuovoMessaggio.setTesto(testoMessaggio);
		nuovoMessaggio.setRichiestaPermesso(richiestaPermessoInstance);
		messaggioRepository.save(nuovoMessaggio);
	}

}
