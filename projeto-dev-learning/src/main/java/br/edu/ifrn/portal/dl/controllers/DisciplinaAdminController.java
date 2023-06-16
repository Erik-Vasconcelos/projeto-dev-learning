package br.edu.ifrn.portal.dl.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.portal.dl.dtos.DisciplinaFormDTO;
import br.edu.ifrn.portal.dl.models.Disciplina;
import br.edu.ifrn.portal.dl.services.DisciplinaService;
import br.edu.ifrn.portal.dl.utils.Pesquisa;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong>entidade Disciplina<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-13
 * @version A0.2
 */

@Controller
@RequestMapping(value = "admin/disciplinas")
public class DisciplinaAdminController {

	@Autowired
	private DisciplinaService disciplinaService;

	/*---------------READ---------------*/

	/*
	 * @GetMapping public ModelAndView getIndex() {
	 *  OK return getIndexComDados(); 
	 * }
	 */

	@GetMapping /* CONSERTAR A URL */
	public ModelAndView disciplinasPaginadas(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<Disciplina> disciplinasPaginadas = disciplinaService.getDisciplinasPaginadas(pageable);
		ModelAndView mv = getIndexTemplate();
		mv.addObject("disciplinas", disciplinasPaginadas);

		return mv;
	}

	@GetMapping("/pesquisa")
	public ModelAndView pesquisarDisciplinas(@PageableDefault(page = 0, size = 10) Pageable pageable,
			@Valid Pesquisa pesquisa, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("***********************Erros********************");
			return getIndexComDados();

		} else {
			Page<Disciplina> disciplinasPaginadas = disciplinaService
					.obterDisciplinasPorNomePaginadas(pesquisa.getValor(), pageable);

			ModelAndView mv = getIndexTemplate();
			mv.addObject("disciplinas", disciplinasPaginadas);

			return mv;
		}
	}

	/*
	 * @GetMapping("/disciplinaspag") public ModelAndView
	 * disciplinasPorPaginacao(@PageableDefault(page = 0, size = 10) Pageable
	 * pageable, ModelAndView model) { Page<Disciplina> pageDisciplina =
	 * disciplinaService.getDisciplinasPaginadas(pageable);
	 * model.addObject("disciplinas", pageDisciplina);
	 * model.setViewName("pg-admin-disciplinas"); return model; }
	 */
	/*---------------CREATE and UPDATE---------------*/

	/*
	 * Web parameter tampering - É quando você expõe o seu objeto final para os
	 * usuários e você "esconde" um campo/atributo e o usuário pode fazer um ataque
	 * criando um campo e informando uma valor para ser enviado ao servidor do
	 * atributo que você escondeu
	 */
	/*
	 * O binding result 're o resultado da estado da enpocotamento da requisicao, se
	 * ela é válida ou tem erros
	 */

	@PostMapping(value = { "/salvar" }) /* OK */
	public ModelAndView salvar(@Valid DisciplinaFormDTO disciplinaDTO, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println("**************Tem erros!!*************");
			ModelAndView mv = getIndexComDados();

			System.out.println(disciplinaDTO.getImagem() == null);
			System.out.println(disciplinaDTO.getImagem().isEmpty());
			return mv;
		} else {
			Disciplina disciplina = disciplinaDTO.toDisciplina();
			disciplinaService.salvar(disciplina);

			return getIndexComDados();
		}
	}

	@GetMapping("/{id}/editar") /* OK */
	public ModelAndView getPaginaEdicao(@PathVariable("id") Long id, DisciplinaFormDTO disciplinaDTO) {
		Disciplina disciplina = disciplinaService.obterPorId(id);
		disciplinaDTO.fromDisciplinaDTO(disciplina);

		ModelAndView mv = new ModelAndView("pg-edit-admin-disciplinas");
		Page<Disciplina> pageDisciplinas = disciplinaService.getDisciplinasPaginadas();
		mv.addObject("disciplinas", pageDisciplinas);
		mv.addObject("id", disciplina.getId());

		return mv;
	}

	/*---------------DELETE---------------*/
	@GetMapping("/{id}/remover") /* OK */
	public String removerDisciplina(@PathVariable("id") Long id) {
		disciplinaService.remover(id);
		return "redirect:/admin/disciplinas";
	}

	/*---------------AXILIARES---------------*/
	@ModelAttribute(value = "disciplinaFormDTO")
	public DisciplinaFormDTO getNovaDisciplina() {
		return new DisciplinaFormDTO();
	}

	@ModelAttribute(value = "pesquisa")
	public Pesquisa getPesquisaModel() {
		return new Pesquisa();
	}

	private ModelAndView getIndexComDados() {
		ModelAndView mv = getIndexTemplate();
		Page<Disciplina> pageDisciplinas = disciplinaService
				.getDisciplinasPaginadas(PageRequest.of(0, 10, Sort.by("id")));
		mv.addObject("disciplinas", pageDisciplinas);

		return mv;
	}

	private ModelAndView getIndexTemplate() {
		ModelAndView mv = new ModelAndView("pg-admin-disciplinas");
		return mv;
	}

}
