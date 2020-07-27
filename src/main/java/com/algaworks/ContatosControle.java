package com.algaworks;



import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContatosControle {

	private static final ArrayList<Contato> LISTA_CONTATO = new ArrayList<>();
	
	static {
		LISTA_CONTATO.add(new Contato("1", "Yuri", "+55 61 1122 3344"));
		LISTA_CONTATO.add(new Contato("2", "Antonia", "+55 61 3344 5566"));
		LISTA_CONTATO.add(new Contato("3", "Lana", "+55 61 7788 9900"));
		LISTA_CONTATO.add(new Contato("4", "Diego", "+55 61 1122 3344"));
		LISTA_CONTATO.add(new Contato("5", "Michelle", "+55 61 7755 9932"));
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/contatos")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("listar");
		
		modelAndView.addObject("contatos", LISTA_CONTATO);
		
		return modelAndView;
	}
	
	@GetMapping("/contatos/novo")
	public ModelAndView novo() {
		
		ModelAndView modelAndView = new ModelAndView("formulario");
		
		modelAndView.addObject("contato", new Contato());
		
		return modelAndView;
	}
	
	@PostMapping("/contatos")
	public String cadastrar(Contato contato) {
		String id = UUID.randomUUID().toString();
		
		contato.setId(id);
		
		LISTA_CONTATO.add(contato);
		
		return "redirect:/contatos";
	}
	
	@GetMapping("/contatos/{id}/editar")
	public ModelAndView editar(@PathVariable String id) {
		ModelAndView modelAndView = new ModelAndView("formulario");
		
		Contato contato = procurarContato(id);
		
		modelAndView.addObject("contato", contato);
		
		return modelAndView;
	}
	
	@PutMapping("/contatos/{id}")
	public String atualizar (Contato contato) {
		Integer indice = procurarIndiceContato(contato.getId());
		
		Contato contatovelho = LISTA_CONTATO.get(indice);
		
		LISTA_CONTATO.remove(contatovelho);
		
		LISTA_CONTATO.add(indice, contato);
		
		return "redirect:/contatos";
	}
	
	@DeleteMapping("/contatos/{id}")
	public String remover (@PathVariable String id) {
		Contato contato = procurarContato(id);
		
		LISTA_CONTATO.remove(contato);
		
		return "redirect:/contatos";
	}
	
	// -------------------------------------- métodos de apoio ----------------------------------------
	
	public Contato procurarContato(String id) {
		Integer indice = procurarIndiceContato(id);
		
		if (indice != null) {
			Contato contato = LISTA_CONTATO.get(indice);
			return contato;
		}
		
		return null;
	}
	
	public Integer procurarIndiceContato(String id) {
		for (int i=0; i <  LISTA_CONTATO.size(); i++) {
			Contato contato = LISTA_CONTATO.get(i);
			
			if (contato.getId().equals(id)) {
				return i;
			}
		}
		
		return null;
	}
	
}
