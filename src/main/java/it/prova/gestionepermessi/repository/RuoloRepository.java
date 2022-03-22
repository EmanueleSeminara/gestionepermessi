package it.prova.gestionepermessi.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.Ruolo;

public interface RuoloRepository extends PagingAndSortingRepository<Ruolo, Long>, JpaSpecificationExecutor<Ruolo> {
	Ruolo findByDescrizioneAndCodice(String descrizione, String codice);
}
