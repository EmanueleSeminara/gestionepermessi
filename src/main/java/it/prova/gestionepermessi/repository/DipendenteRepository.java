package it.prova.gestionepermessi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteRepository
		extends PagingAndSortingRepository<Dipendente, Long>, JpaSpecificationExecutor<Dipendente> {

	@Query("from Dipendente d left join fetch d.richiestePermesso left join fetch d.utente where d.id = ?1")
	public Optional<Dipendente> findByIdEager(Long idDipendente);

	List<Dipendente> findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(String cognome,
			String nome);

	@Query("from Dipendente where utente_id = ?1")
	Dipendente finbByUtente(Long idUtente);

}
