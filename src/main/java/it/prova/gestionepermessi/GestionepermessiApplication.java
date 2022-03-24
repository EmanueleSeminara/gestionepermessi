package it.prova.gestionepermessi;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.Sesso;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@SpringBootApplication
public class GestionepermessiApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private DipendenteService dipendenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(GestionepermessiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente", "ROLE_DIPENDENTE_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Dipendente", "ROLE_DIPENDENTE_USER"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office", "ROLE_BO_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Back Office", "ROLE_BO_USER"));
		}

		// a differenza degli altri progetti cerco solo per username perche' se vado
		// anche per password ogni volta ne inserisce uno nuovo, inoltre l'encode della
		// password non lo
		// faccio qui perche gia lo fa il service di utente, durante inserisciNuovo
		if (utenteServiceInstance.findByUsername("m.rossi") == null) {
			Dipendente admin = new Dipendente("Mario", "Rossi");
//			
			dipendenteServiceInstance.inserisciDipendenteEUtenteConRuoli(admin,
					ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getUtente().getId());
		}

		if (utenteServiceInstance.findByUsername("c.corsello") == null) {
			Date dataNascita1 = new SimpleDateFormat("dd/MM/yyyy").parse("20/06/1999");
			Date dataAssunzione1 = new SimpleDateFormat("dd/MM/yyyy").parse("10/02/2022");
			Dipendente admin = new Dipendente("Calogero", "Corsello", "CRSCGR99H20B602J", dataNascita1, dataAssunzione1,
					new Date(), Sesso.MASCHIO);

//			
			dipendenteServiceInstance.inserisciDipendenteEUtenteConRuoli(admin,
					ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getUtente().getId());
		}

//		if (utenteServiceInstance.findByUsername("user") == null) {
//			Utente classicUser = new Utente("user", "user", "Antonio", "Verdi", new Date());
//			classicUser.getRuoli()
//					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER"));
//			utenteServiceInstance.inserisciNuovo(classicUser);
//			// l'inserimento avviene come created ma io voglio attivarlo
//			utenteServiceInstance.changeUserAbilitation(classicUser.getId());
//		}
//
//		if (utenteServiceInstance.findByUsername("user1") == null) {
//			Utente classicUser1 = new Utente("user1", "user1", "Antonioo", "Verdii", new Date());
//			classicUser1.getRuoli()
//					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER"));
//			utenteServiceInstance.inserisciNuovo(classicUser1);
//			// l'inserimento avviene come created ma io voglio attivarlo
//			utenteServiceInstance.changeUserAbilitation(classicUser1.getId());
//		}
//
//		if (utenteServiceInstance.findByUsername("user2") == null) {
//			Utente classicUser2 = new Utente("user2", "user2", "Antoniooo", "Verdiii", new Date());
//			classicUser2.getRuoli()
//					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER"));
//			utenteServiceInstance.inserisciNuovo(classicUser2);
//			// l'inserimento avviene come created ma io voglio attivarlo
//			utenteServiceInstance.changeUserAbilitation(classicUser2.getId());
//		}

	}

}
