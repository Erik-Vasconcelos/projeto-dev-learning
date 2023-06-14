package br.edu.ifrn.portal.dl.controllers;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.portal.dl.models.Disciplina;
import br.edu.ifrn.portal.dl.services.DisciplinaService;
import br.edu.ifrn.portal.dl.services.GravaImagem;

/**
 * 
 * @author erikv
 * @since 13/06/2023
 * 
 */

@Controller
@RequestMapping(value = "/disciplinas")
public class DisciplinaController {
	
	@Autowired
	private DisciplinaService disciplinaService;
	
	@GetMapping
	public ModelAndView getPaginaDisciplinasPaginadas() {
		return getPaginaComRegistros();
	}
	
	@GetMapping("/disciplinaspag")
	public ModelAndView disciplinasPorPaginacao(@PageableDefault(page = 0, size = 10) Pageable pageable, ModelAndView model) {
		Page<Disciplina> pageDisciplina = disciplinaService.getDisciplinasPaginadas(pageable);
		model.addObject("disciplinas", pageDisciplina);
		model.setViewName("pagina_disciplinas");
		return model;
	}
	
	@PostMapping(value= {"/salvar", "/editar{id}"})
	public Object salvar(@RequestParam("file") MultipartFile file, @Valid Disciplina disciplina, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return redirecionarFluxo(file, disciplina, result, redirect);
		}
		
		String imagem;
		if(!file.isEmpty()) {
			imagem = GravaImagem.getImagemEncoded(file);
			disciplina.setImagem(imagem);
		}else if(!file.isEmpty() && disciplina != null){
			imagem = disciplinaService.getImage(disciplina.getId());
			disciplina.setImagem(imagem);
		}
		
		disciplinaService.salvar(disciplina);
		return "redirect:/disciplinas";
	}
	
	private ModelAndView redirecionarFluxo(MultipartFile file, Disciplina disciplina, BindingResult result, RedirectAttributes redirect) {
		ModelAndView mv = getPaginaComRegistros();
		
		if(file.isEmpty()) {
			disciplina.setImagem(disciplinaService.getImage(disciplina.getId()));
		}else {
			disciplina.setImagem(GravaImagem.getImagemEncoded(file));
		}
		
		mv.addObject("erros", result.getAllErrors());
		mv.addObject("disciplina", disciplina);
		
		return mv;
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView getPaginaEdicao(@PathVariable("id") Long id) {
		Disciplina disciplina = disciplinaService.obterPorId(id);
		
		ModelAndView mv = getPaginaComRegistros();
		mv.addObject("disciplina", disciplina);
		
		return mv;
	}
	
	@GetMapping("/remover/{id}")
	public String removerDisciplina(@PathVariable("id") Long id) {
		disciplinaService.remover(id);
		return "redirect:/disciplinas";
	}
	
	/*@PostMapping("/pesquisa")
	public ModelAndView obterPorNome(@RequestParam(name = "nome") String nome) {*/
		/*
		 * List<Disciplina> disciplinas = disciplinaService.obterPorParteNome(nome);
		 * 
		 * ModelAndView mv = new ModelAndView("pagina_disciplinas");
		 * mv.addObject("disciplinas", disciplinas); mv.addObject("valorPesquisa",
		 * nome); return mv;
		 */
		
		/*ModelAndView mv = new ModelAndView("pagina_disciplinas");
		mv.addObject("disciplinas", disciplinaService.obterPorParteNome(nome));
		mv.addObject("valorPesquisa", nome);
		return mv;
	}
	
	@GetMapping("/pesquisapag")
	public ModelAndView disciplinasPesquisaPorPaginacao(@RequestParam(name = "nome") String nome, @PageableDefault(page = 0, size = 10) Pageable pageable, ModelAndView model) {
		Page<Disciplina> pageDisciplina = disciplinaService.obterPorParteNome(nome, pageable.getPageNumber(), pageable.getPageSize());
		model.addObject("disciplinas", pageDisciplina);
		model.addObject("valorPesquisa", nome);
		model.setViewName("pagina_disciplinas");
		return model;
	}*/
	
	private ModelAndView getPaginaComRegistros() {
		ModelAndView mv = new ModelAndView("pagina_disciplinas");
		Page<Disciplina> pageDisciplinas =  disciplinaService.getDisciplinasPaginadas();
		mv.addObject("disciplinas", pageDisciplinas);
		
		return mv;
	}
	
}
