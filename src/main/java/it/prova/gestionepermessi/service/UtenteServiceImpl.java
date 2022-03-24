package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.DipendenteRepository;
import it.prova.gestionepermessi.repository.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${user.default.password}")
	private String defaultPassword;

	@Override
	@Transactional(readOnly = true)
	public Utente caricaSingoloElemento(Long id) {
		return utenteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Utente caricaSingoloElementoEager(Long id) {
		return utenteRepository.findByIdEager(id).orElse(null);
	}

	@Override
	@Transactional
	public void rimuovi(Utente utenteInstance) {
		utenteRepository.delete(utenteInstance);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Utente> findByExample(Utente example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Utente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getDipendente().getNome()))
				predicates.add(cb.like(cb.upper(root.join("dipendente").get("nome")),
						"%" + example.getDipendente().getNome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getDipendente().getCognome()))
				predicates.add(cb.like(cb.upper(root.join("dipendente").get("cognome")),
						"%" + example.getDipendente().getCognome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getUsername()))
				predicates
						.add(cb.like(cb.upper(root.get("username")), "%" + example.getUsername().toUpperCase() + "%"));

			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			if (example.getDateCreated() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreated"), example.getDateCreated()));
			if (example.getRuoli() != null && !example.getRuoli().isEmpty()) {

				predicates.add(root.join("ruoli").in(example.getRuoli()));
			}
			root.fetch("ruoli", JoinType.LEFT);
			query.distinct(true);
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return utenteRepository.findAll(specificationCriteria, paging);
	}

	@Override
	@Transactional(readOnly = true)
	public Utente eseguiAccesso(String username, String password) {
		return utenteRepository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return utenteRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloElemento(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Override
	@Transactional
	public Utente findByUsername(String username) {
		return utenteRepository.findByUsername(username).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Utente> listAllElements() {
		return (List<Utente>) utenteRepository.findAll();
	}

	@Override
	@Transactional
	public void aggiornaRuoliUtente(Utente utenteInstance) {
		Utente utenteReloaded = utenteRepository.findByIdEager(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");

		utenteReloaded.setRuoli(utenteInstance.getRuoli());

		utenteRepository.save(utenteReloaded);
	}

}
