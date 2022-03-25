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

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.RuoloDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RuoloService;

@Controller
@RequestMapping(value = "/dipendente")
public class DipendenteController {
	@Autowired
	private DipendenteService dipendenteService;

	@Autowired
	private RuoloService ruoloService;

	@GetMapping
	public ModelAndView listAllDipendenti() {
		ModelAndView mv = new ModelAndView();
		List<Dipendente> dipendenti = dipendenteService.listAllElements();
		mv.addObject("dipendente_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		mv.addObject("path", "gestioneDipendenti");
		mv.setViewName("dipendente/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchDipendente(Model model) {
		model.addAttribute("path", "gestioneDipendenti");
		return "dipendente/search";
	}

	@PostMapping("/list")
	public String listDipendenti(DipendenteDTO dipendenteExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {
		List<Dipendente> dipendenti = dipendenteService
				.findByExample(dipendenteExample.buildDipendenteModel(true), pageNo, pageSize, sortBy).getContent();
		model.addAttribute("dipendente_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		model.addAttribute("path", "gestioneDipendenti");
		return "dipendente/list";
	}

	@GetMapping("/show/{idDipendente}")
	public String showDipendente(@PathVariable(required = true) Long idDipendente, Model model) {
		model.addAttribute("show_dipendente_attr",
				DipendenteDTO.buildDipendenteDTOFromModel(dipendenteService.caricaSingoloElementoEager(idDipendente)));
		model.addAttribute("path", "gestioneDipendenti");

		return "dipendente/show";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("ruoli_totali_attr",
				RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAllElementsExceptBy(new Long[] {
						ruoloService.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN").getId() })));
		model.addAttribute("insert_dipendente_attr", new DipendenteDTO());
		model.addAttribute("path", "gestioneDipendenti");
		return "dipendente/insert";
	}

	// per la validazione devo usare i groups in quanto nella insert devo validare
	// la pwd, nella edit no
	@PostMapping("/save")
	public String save(@Validated @ModelAttribute("insert_dipendente_attr") DipendenteDTO dipendenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAllElementsExceptBy(
					new Long[] { ruoloService.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN").getId() }));
			return "dipendente/insert";
		}
		dipendenteService.inserisciDipendenteEUtenteConRuoli(dipendenteDTO.buildDipendenteModel(true),
				dipendenteDTO.getRuoloId());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente";
	}

	@GetMapping("/edit/{idDipendente}")
	public String edit(@PathVariable(required = true) Long idDipendente, Model model) {
		Dipendente dipendenteModel = dipendenteService.caricaSingoloElementoEager(idDipendente);
		model.addAttribute("edit_dipendente_attr", DipendenteDTO.buildDipendenteDTOFromModel(dipendenteModel));
		model.addAttribute("path", "gestioneDipendenti");
		return "dipendente/edit";
	}

	@PostMapping("/update")
	public String update(@Validated @ModelAttribute("edit_dipendente_attr") DipendenteDTO dipendenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "dipendente/edit";
		}
		dipendenteService.aggiornaDipendenteEUtente(dipendenteDTO.buildDipendenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente";
	}
}
