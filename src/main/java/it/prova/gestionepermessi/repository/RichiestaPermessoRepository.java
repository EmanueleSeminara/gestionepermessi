package it.prova.gestionepermessi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoRepository
		extends PagingAndSortingRepository<RichiestaPermesso, Long>, JpaSpecificationExecutor<RichiestaPermesso> {
	@Query("from RichiestaPermesso r left join fetch r.attachment left join fetch r.dipendente where r.id = ?1")
	public Optional<RichiestaPermesso> findByIdEager(Long idMessaggio);

	@Query("from RichiestaPermesso r left join fetch r.attachment a left join fetch r.dipendente d left join fetch d.utente u where u.username = ?1")
	public List<RichiestaPermesso> findByUsername(String usernameInput);
}
