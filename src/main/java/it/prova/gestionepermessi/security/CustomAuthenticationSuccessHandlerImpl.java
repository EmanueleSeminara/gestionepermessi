package it.prova.gestionepermessi.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import it.prova.gestionepermessi.dto.UtenteInfoDTO;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.MessaggioRepository;
import it.prova.gestionepermessi.repository.UtenteRepository;

@Component
public class CustomAuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private MessaggioRepository messaggioRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// voglio mettere in sessione uno userInfo perché spring security mette solo un
		// principal da cui attingere username
		Utente utenteFromDb = utenteRepository.findByUsername(authentication.getName()).orElseThrow(
				() -> new UsernameNotFoundException("Username " + authentication.getName() + " not found"));

		UtenteInfoDTO utenteParziale = new UtenteInfoDTO();
		utenteParziale.setNome(utenteFromDb.getDipendente().getNome());
		utenteParziale.setCognome(utenteFromDb.getDipendente().getCognome());
		request.getSession().setAttribute("userInfo", utenteParziale);
		if (utenteFromDb.isBackOffice()) {
			request.getSession().setAttribute("message_count", messaggioRepository.countByLetto(false));
		}
		response.sendRedirect("home");

	}

}
