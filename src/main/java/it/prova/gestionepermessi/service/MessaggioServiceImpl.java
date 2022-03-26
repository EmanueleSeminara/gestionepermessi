package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.List;

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

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.repository.MessaggioRepository;

@Service
public class MessaggioServiceImpl implements MessaggioService {

	@Autowired
	private MessaggioRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Messaggio> listAllElements() {
		return (List<Messaggio>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Messaggio caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Messaggio messaggioInstance) {
		repository.save(messaggioInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Messaggio messaggioInstance) {
		repository.save(messaggioInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Messaggio messaggioInstance) {
		repository.delete(messaggioInstance);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Messaggio> findByExample(Messaggio example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Messaggio> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getTesto()))
				predicates.add(cb.like(cb.upper(root.get("testo")), "%" + example.getTesto().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getOggetto()))
				predicates.add(cb.like(cb.upper(root.get("oggetto")), "%" + example.getOggetto().toUpperCase() + "%"));

			if (example.getLetto() != null)
				predicates.add(cb.equal(root.get("letto"), example.getLetto()));

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
	public Messaggio caricaSingoloElementoEager(Long id) {
		return repository.findByIdEager(id).orElse(null);
	}

	@Override
	public int numeroMessaggiDaLeggere() {
		return repository.countByLetto(false);
	}

	@Override
	public Messaggio leggiMessaggio(Long idMessaggio) {
		Messaggio messaggioDaLeggere = repository.findByIdEager(idMessaggio).orElse(null);
		messaggioDaLeggere.setLetto(true);
		repository.save(messaggioDaLeggere);
		return messaggioDaLeggere;

	}

}
