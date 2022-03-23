package it.prova.gestionepermessi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioRepository
		extends PagingAndSortingRepository<Messaggio, Long>, JpaSpecificationExecutor<Messaggio> {
	@Query("from Messaggio m left join fetch m.richiestaPermesso r left join fetch r.dipendente where d.id = ?1")
	public Optional<Messaggio> findByIdEager(Long idMessaggio);
}
