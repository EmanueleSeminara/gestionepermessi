package it.prova.gestionepermessi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;

public interface UtenteRepository extends PagingAndSortingRepository<Utente, Long>, JpaSpecificationExecutor<Utente> {
	@EntityGraph(attributePaths = { "ruoli", "dipendente" })
	Optional<Utente> findByUsername(String username);

	@Query("from Utente u left join fetch u.ruoli left join fetch u.dipendente where u.id = ?1")
	Optional<Utente> findByIdEager(Long id);

	Utente findByUsernameAndPassword(String username, String password);

	// caricamento eager, ovviamente si può fare anche con jpql
	@EntityGraph(attributePaths = { "ruoli" })
	Utente findByUsernameAndPasswordAndStato(String username, String password, StatoUtente stato);
}
