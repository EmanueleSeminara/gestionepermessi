package it.prova.gestionepermessi.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.exceptions.RichiestaPermessoConDataInizioSuperataException;
import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.UtenteRepository;
import it.prova.gestionepermessi.service.AttachmentService;
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

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private AttachmentService attachmentService;

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
			checkMessageIfBO(model);
			mv.setViewName("richiestapermesso/list");
		} else if (roles.contains("ROLE_DIPENDENTE_USER")) {
			List<RichiestaPermesso> richiestePermesso = richiestaPermessoService
					.listAllElementsByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

			mv.addObject("richiestePermesso_list_attribute",
					RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermesso));
			mv.addObject("path", "gestioneRichiestePermesso");
			mv.setViewName("richiestapermesso/list");
		} else {
			mv.addObject("path", "home");
			mv.setViewName("home");
		}

		return mv;
	}

	@GetMapping("/search")
	public String searchDipendente(Model model) {
		model.addAttribute("path", "ricercaPermessi");
		checkMessageIfBO(model);
		return "richiestapermesso/search";
	}

	@PostMapping("/list")
	public String listRichiestaPermesso(RichiestaPermessoDTO richiestaPermessoExample,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, Model model) {
		Set<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(r -> r.getAuthority()).collect(Collectors.toSet());

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (roles.contains("ROLE_DIPENDENTE_USER")) {
			richiestaPermessoExample.setDipendente(utenteRepository.findByUsername(username).get().getDipendente());
		}
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService
				.findByExample(richiestaPermessoExample.buildRichiestaPermessoModel(true), pageNo, pageSize, sortBy)
				.getContent();
		model.addAttribute("richiestePermesso_list_attribute",
				RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermessi));
		model.addAttribute("path", "ricercaPermessi");
		checkMessageIfBO(model);
		return "richiestapermesso/list";
	}

	@GetMapping("/show/{idRichiestaPermesso}")
	public String showRichiestaPermesso(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		model.addAttribute("show_richiestaPermesso_attr", RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(
				richiestaPermessoService.caricaSingoloElementoEager(idRichiestaPermesso)));
		checkMessageIfBO(model);
		model.addAttribute("path", "ricercaPermessi");

		return "richiestapermesso/show";
	}

	@PostMapping("/cambiastato")
	public String cambiaStato(
			@RequestParam(name = "idRichiestaPermessoForChangingStato", required = true) Long idRichiestaPermesso) {
		richiestaPermessoService.changeRichiestaPermessoStato(idRichiestaPermesso);
		return "redirect:/richiestapermesso";
	}

	private void checkMessageIfBO(Model modelInput) {
		Set<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(r -> r.getAuthority()).collect(Collectors.toSet());
		if (roles.contains("ROLE_BO_USER")) {
			modelInput.addAttribute("message_count", messaggioService.numeroMessaggiDaLeggere());
		}
	}

	@GetMapping("/searchpersonal")
	public String searchMessaggioPersonal(Model model) {
		model.addAttribute("path", "gestioneRichiestePermesso");
		return "richiestapermesso/searchPersonal";
	}

	@GetMapping("/insert")
	public String create(Model model) {

		model.addAttribute("insert_richiestaPermesso_attr", new RichiestaPermessoDTO());
		model.addAttribute("path", "gestioneRichiestePermesso");
		return "richiestapermesso/insert";
	}

	// per la validazione devo usare i groups in quanto nella insert devo validare
	// la pwd, nella edit no
	@PostMapping("/save")
	public String save(
			@Validated @ModelAttribute("insert_richiestaPermesso_attr") RichiestaPermessoDTO richiestaPermessoDTO,
			@RequestParam("file") MultipartFile file, BindingResult result, Model model,
			RedirectAttributes redirectAttrs) {
		if (result.hasErrors()) {
			return "richiestapermesso/insert";
		}
		if (file == null || file.isEmpty()) {
			model.addAttribute("errorMessage", "Inserire dei valori");
			return "richiestapermesso/insert";
		}

		richiestaPermessoDTO.setAttachment(new Attachment());
		try {
			richiestaPermessoDTO.getAttachment().setContentType(file.getContentType());
			richiestaPermessoDTO.getAttachment().setNomeFile(file.getOriginalFilename());
			richiestaPermessoDTO.getAttachment().setPayload(file.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Problema nell'upload file", e);
		}
		richiestaPermessoService.inserisciNuovaRichiestaPermessoECreaMessaggio(
				richiestaPermessoDTO.buildRichiestaPermessoModel(true),
				SecurityContextHolder.getContext().getAuthentication().getName());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiestapermesso";
	}

	@GetMapping("/edit/{idRichiestaPermesso}")
	public String edit(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		RichiestaPermesso richiestaPermessoModel = richiestaPermessoService
				.caricaSingoloElementoEager(idRichiestaPermesso);
		model.addAttribute("edit_richiestaPermesso_attr",
				RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaPermessoModel));
		model.addAttribute("path", "gestioneRichiestePermesso");
		return "richiestapermesso/edit";
	}

	@PostMapping("/update")
	public String update(
			@Validated @ModelAttribute("edit_richiestaPermesso_attr") RichiestaPermessoDTO richiestaPermessoDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "richiestapermesso/edit";
		}

		try {
			richiestaPermessoService.aggiorna(richiestaPermessoDTO.buildRichiestaPermessoModel(true));
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} catch (NullPointerException ex) {
			redirectAttrs.addFlashAttribute("errorMessage", "Impossibile modificare il permesso");
		} catch (IllegalArgumentException ex) {
			redirectAttrs.addFlashAttribute("errorMessage", "Impossibile modificare il permesso");
		}

		return "redirect:/richiestapermesso";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam(name = "idRichiestaPermessoForDelete", required = true) Long idRichiestaPermesso,
			RedirectAttributes redirectAttrs) {
		try {
			richiestaPermessoService.rimuovi(idRichiestaPermesso);
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} catch (RichiestaPermessoConDataInizioSuperataException ex) {
			redirectAttrs.addFlashAttribute("errorMessage", "Impossibile eliminare il permesso");
		}
		return "redirect:/richiestapermesso";
	}

	@GetMapping("/showAttachment/{idAttachment}")
	public ResponseEntity<byte[]> showAttachment(@PathVariable(required = true) Long idAttachment) {

		Attachment file = attachmentService.caricaSingoloElemento(idAttachment);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getNomeFile() + "\"")
				.body(file.getPayload());

	}
}
