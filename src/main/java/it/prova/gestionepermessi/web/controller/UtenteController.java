package it.prova.gestionepermessi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.RuoloDTO;
import it.prova.gestionepermessi.dto.UtenteDTO;
import it.prova.gestionepermessi.dto.UtenteToShowDTO;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;
import it.prova.gestionepermessi.validation.ValidationNoUsername;

@Controller
@RequestMapping(value = "/utente")
public class UtenteController {
	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RuoloService ruoloService;

	@GetMapping
	public ModelAndView listAllUtenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllElements();
		mv.addObject("utente_list_attribute", UtenteToShowDTO.createUtenteToShowDTOListFromModelList(utenti));
		mv.addObject("path", "gestioneutenze");
		mv.setViewName("utente/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchUtente(Model model) {
		model.addAttribute("ruoli_totali_attr",
				RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAllElements()));
		model.addAttribute("path", "gestioneutenze");
		return "utente/search";
	}

	@PostMapping("/list")
	public String listUtenti(UtenteDTO utenteExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {
		List<Utente> utenti = utenteService
				.findByExample(utenteExample.buildUtenteModel(true, true), pageNo, pageSize, sortBy).getContent();
		model.addAttribute("utente_list_attribute", UtenteToShowDTO.createUtenteToShowDTOListFromModelList(utenti));
		model.addAttribute("path", "gestioneutenze");
		return "utente/list";
	}

	@GetMapping("/show/{idUtente}")
	public String showUtente(@PathVariable(required = true) Long idUtente, Model model) {
		model.addAttribute("show_utente_attr",
				UtenteToShowDTO.buildUtenteToShowDTOFromModel(utenteService.caricaSingoloElementoEager(idUtente)));
		model.addAttribute("path", "gestioneutenze");

		return "utente/show";
	}

	@GetMapping("/edit/{idUtente}")
	public String edit(@PathVariable(required = true) Long idUtente, Model model) {
		Utente utenteModel = utenteService.caricaSingoloElementoEager(idUtente);
		model.addAttribute("edit_utente_attr", UtenteDTO.buildUtenteDTOFromModel(utenteModel));
		model.addAttribute("ruoli_totali_attr",
				RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAllElements()));
		model.addAttribute("path", "gestioneutenze");
		return "utente/edit";
	}

	@PostMapping("/update")
	public String update(@Validated(ValidationNoUsername.class) @ModelAttribute("edit_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("ruoli_totali_attr",
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAllElements()));
			return "utente/edit";
		}
		utenteService.aggiornaRuoliUtente(utenteDTO.buildUtenteModel(true, false));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@PostMapping("/cambiastato")
	public String cambiaStato(@RequestParam(name = "idUtenteForChangingStato", required = true) Long idUtente) {
		utenteService.changeUserAbilitation(idUtente);
		return "redirect:/utente";
	}
}
