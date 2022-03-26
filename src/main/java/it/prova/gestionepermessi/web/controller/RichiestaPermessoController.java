package it.prova.gestionepermessi.web.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.MessaggioService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;

@Controller
@SessionAttributes("message_count")
@RequestMapping(value = "/richiestapermesso")
public class RichiestaPermessoController {
	@Autowired
	private RichiestaPermessoService richiestaPermessoService;

	@Autowired
	private MessaggioService messaggioService;

	@GetMapping
	public ModelAndView listAllRichiestePermesso(Model model) {
		ModelAndView mv = new ModelAndView();
		Set<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(r -> r.getAuthority()).collect(Collectors.toSet());
		if (roles.contains("ROLE_BO_USER")) {
			List<RichiestaPermesso> richiestePermesso = richiestaPermessoService.listAllElements();
			mv.addObject("richiestePermesso_list_attribute",
					RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermesso));
			mv.addObject("path", "ricercaPermessi");
			checkMessaggeIfBO(model);
			mv.setViewName("richiestapermesso/list");
		} else if (roles.contains("ROLE_DIPENDENTE_USER")) {

		} else {
			mv.addObject("path", "home");
			mv.setViewName("home");
		}

		return mv;
	}

	@GetMapping("/search")
	public String searchDipendente(Model model) {
		model.addAttribute("path", "ricercaPermessi");
		checkMessaggeIfBO(model);
		return "richiestapermesso/search";
	}

	@PostMapping("/list")
	public String listRichiestaPermesso(RichiestaPermessoDTO richiestaPermessoExample,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, Model model) {
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService
				.findByExample(richiestaPermessoExample.buildRichiestaPermessoModel(true), pageNo, pageSize, sortBy)
				.getContent();
		model.addAttribute("richiestePermesso_list_attribute",
				RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermessi));
		model.addAttribute("path", "ricercaPermessi");
		checkMessaggeIfBO(model);
		return "richiestapermesso/list";
	}

	@GetMapping("/show/{idRichiestaPermesso}")
	public String showRichiestaPermesso(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		System.out.println("SEI DENTRO");
		model.addAttribute("show_richiestaPermesso_attr", RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(
				richiestaPermessoService.caricaSingoloElementoEager(idRichiestaPermesso)));
		checkMessaggeIfBO(model);
		model.addAttribute("path", "ricercaPermessi");

		return "richiestapermesso/show";
	}

	@PostMapping("/cambiaStato")
	public String cambiaStato(
			@RequestParam(name = "idRichiestaPermessoForChangingStato", required = true) Long idRichiestaPermesso) {
		richiestaPermessoService.changeRichiestaPermessoStato(idRichiestaPermesso);
		return "redirect:/richiestapermesso";
	}

	private void checkMessaggeIfBO(Model modelInput) {
		Set<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(r -> r.getAuthority()).collect(Collectors.toSet());
		if (roles.contains("ROLE_BO_USER")) {
			modelInput.addAttribute("message_count", messaggioService.numeroMessaggiDaLeggere());
		}
	}
}
