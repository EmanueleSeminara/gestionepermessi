package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.service.MessaggioService;

@Controller
@RequestMapping(value = "/messaggio")
public class MessaggioController {
	@Autowired
	private MessaggioService messaggioService;

	@GetMapping
	public ModelAndView listAllMessaggi(Model model) {
		ModelAndView mv = new ModelAndView();
		List<Messaggio> richiestePermesso = messaggioService.listAllElements();
		mv.addObject("messaggio_list_attribute", MessaggioDTO.createMessaggioDTOListFromModelList(richiestePermesso));
		mv.addObject("path", "gestioneMessaggi");
		mv.setViewName("messaggio/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchMessaggio(Model model) {
		model.addAttribute("path", "gestioneMessaggi");
		return "messaggio/search";
	}

	@PostMapping("/list")
	public String listMessaggio(MessaggioDTO messaggioExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			Model model) {
		System.out.println("SEI DENTRO");
		System.out.println("RISULTATO: " + messaggioExample.buildMessaggioModel(true));
		List<Messaggio> messaggi = messaggioService
				.findByExample(messaggioExample.buildMessaggioModel(true), pageNo, pageSize, sortBy).getContent();
		model.addAttribute("messaggio_list_attribute", MessaggioDTO.createMessaggioDTOListFromModelList(messaggi));
		model.addAttribute("path", "gestioneMessaggi");
		return "messaggio/list";
	}

	@GetMapping("/show/{idMessaggio}")
	public String showMessaggio(@PathVariable(required = true) Long idMessaggio, Model model) {
		model.addAttribute("show_messaggio_attr",
				MessaggioDTO.buildMessaggioDTOFromModel(messaggioService.leggiMessaggio(idMessaggio)));
		model.addAttribute("path", "gestioneMessaggi");
		return "messaggio/show";
	}
}
