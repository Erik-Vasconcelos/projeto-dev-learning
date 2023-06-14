package br.edu.ifrn.portal.dl.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.services.TecnologiaService;

/**
 * 
 * @author erikv
 * @since 08/06/2023
 * 
 */

@Controller
@RequestMapping(value = "/tecnologias")
public class TecnologiaController {
	
	@Autowired
	private TecnologiaService tecnologiaService;
	
	@GetMapping
	public ModelAndView getPaginaTecnologiasPaginadas() {
		return getPaginaComRegistros();
	}
	
	@GetMapping("/tecnologiaspag")
	public ModelAndView tecnologiasPorPaginacao(@PageableDefault(page = 0, size = 10) Pageable pageable, ModelAndView model) {
		Page<Tecnologia> pageTecnologia = tecnologiaService.getTecnologiasPaginadas(pageable);
		model.addObject("tecnologias", pageTecnologia);
		model.setViewName("pagina_tecnologias");
		return model;
	}
	
	@PostMapping(value= {"/salvar", "/editar{id}"})
	public Object salvar(@Valid Tecnologia tecnologia, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return redirecionarFluxo(tecnologia, result, redirect);
		}
		
		tecnologiaService.salvar(tecnologia);
		return "redirect:/tecnologias";
	}
	
	private ModelAndView redirecionarFluxo(Tecnologia tecnologia, BindingResult result, RedirectAttributes redirect) {
		ModelAndView mv = getPaginaComRegistros();
		
		mv.addObject("erros", result.getAllErrors());
		mv.addObject("tecnologia", tecnologia);
		
		return mv;
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView getPaginaEdicao(@PathVariable("id") Long id) {
		Tecnologia tecnologia = tecnologiaService.obterPorId(id);
		
		ModelAndView mv = getPaginaComRegistros();
		mv.addObject("tecnologia", tecnologia);
		
		return mv;
	}
	
	@GetMapping("/remover/{id}")
	public String removerTecnologia(@PathVariable("id") Long id) {
		tecnologiaService.remover(id);
		return "redirect:/tecnologias";
	}
	
	@PostMapping("/pesquisa")
	public ModelAndView obterPorNome(@RequestParam(name = "nome") String nome) {
		/*
		 * List<Tecnologia> tecnologias = tecnologiaService.obterPorParteNome(nome);
		 * 
		 * ModelAndView mv = new ModelAndView("pagina_tecnologias");
		 * mv.addObject("tecnologias", tecnologias); mv.addObject("valorPesquisa",
		 * nome); return mv;
		 */
		
		ModelAndView mv = new ModelAndView("pagina_tecnologias");
		mv.addObject("tecnologias", tecnologiaService.obterPorParteNome(nome));
		mv.addObject("valorPesquisa", nome);
		return mv;
	}
	
	@GetMapping("/pesquisapag")
	public ModelAndView tecnologiasPesquisaPorPaginacao(@RequestParam(name = "nome") String nome, @PageableDefault(page = 0, size = 10) Pageable pageable, ModelAndView model) {
		Page<Tecnologia> pageTecnologia = tecnologiaService.obterPorParteNome(nome, pageable.getPageNumber(), pageable.getPageSize());
		model.addObject("tecnologias", pageTecnologia);
		model.addObject("valorPesquisa", nome);
		model.setViewName("pagina_tecnologias");
		return model;
	}
	
	private ModelAndView getPaginaComRegistros() {
		ModelAndView mv = new ModelAndView("pagina_tecnologias");
		Page<Tecnologia> pageTecnologias =  tecnologiaService.getTecnologiasPaginadas();
		mv.addObject("tecnologias", pageTecnologias);
		
		return mv;
	}
	
}
