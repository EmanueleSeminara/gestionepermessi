package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.RichiestaPermessoService;

@Controller
@RequestMapping(value = "/richiestapermesso")
public class RichiestaPermessoController {
	@Autowired
	private RichiestaPermessoService richiestaPermessoService;

	@GetMapping
	public ModelAndView listAllRichiestePermesso() {
		ModelAndView mv = new ModelAndView();
		List<RichiestaPermesso> richiestePermesso = richiestaPermessoService.listAllElements();
		mv.addObject("richiestePermesso_list_attribute",
				RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermesso));
		mv.addObject("path", "ricercaPermessi");
		mv.setViewName("richiestapermesso/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchDipendente(Model model) {
		model.addAttribute("path", "ricercaPermessi");
		return "richiestapermesso/search";
	}

	@PostMapping("/list")
	public String listRichiestaPermesso(RichiestaPermessoDTO richiestaPermessoExample,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, ModelMap model) {
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService
				.findByExample(richiestaPermessoExample.buildRichiestaPermessoModel(true), pageNo, pageSize, sortBy)
				.getContent();
		model.addAttribute("richiestePermesso_list_attribute",
				RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermessi));
		model.addAttribute("path", "ricercaPermessi");
		return "richiestapermesso/list";
	}
}
