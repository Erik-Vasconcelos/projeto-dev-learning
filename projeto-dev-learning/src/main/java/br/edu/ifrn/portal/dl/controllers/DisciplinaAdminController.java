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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.portal.dl.dtos.DisciplinaFormDTO;
import br.edu.ifrn.portal.dl.models.Disciplina;
import br.edu.ifrn.portal.dl.services.DisciplinaService;
import br.edu.ifrn.portal.dl.utils.Mensagem;
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

	@GetMapping /* OK */
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
			return getIndexComDados(); /* TODO */

		} else {
			Page<Disciplina> disciplinasPaginadas = disciplinaService
					.obterDisciplinasPorNomePaginadas(pesquisa.getValor(), pageable);
			ModelAndView mv = getIndexTemplate();
			mv.addObject("disciplinas", disciplinasPaginadas);

			return mv;
		}
	}

	/*---------------CREATE and UPDATE---------------*/

	@PostMapping(value = { "/salvar" })
	public ModelAndView salvar(@Valid DisciplinaFormDTO disciplinaDTO, BindingResult result,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			ModelAndView mv = getIndexComDados();
			mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));

			System.out.println(disciplinaDTO.getImagem().isEmpty());
			return mv;
		} else {
			Disciplina disciplina = disciplinaDTO.toDisciplina();
			disciplinaService.salvar(disciplina);
			redirect.addFlashAttribute("mensagem", new Mensagem("Disciplina inserida com sucesso!"));

			return new ModelAndView("redirect:/admin/disciplinas");
		}
	}

	@GetMapping("/{id}/editar")
	public ModelAndView getPaginaEdicao(@PathVariable("id") Long id, DisciplinaFormDTO disciplinaDTO,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {
		try {
			Disciplina disciplina = disciplinaService.obterPorId(id);
			disciplinaDTO.fromDisciplinaDTO(disciplina);
	
			ModelAndView mv = new ModelAndView("pg-edit-admin-disciplinas");
			Page<Disciplina> pageDisciplinas = disciplinaService.getDisciplinasPaginadas(pageable);
			mv.addObject("disciplinas", pageDisciplinas);
			mv.addObject("id", disciplina.getId());
			mv.addObject("mensagem", new Mensagem("Disciplina #" + id + " foi atualizada com sucesso!"));
	
			return mv;
		}catch (IllegalArgumentException e) {
			ModelAndView mv = disciplinasPaginadas(pageable);
			mv.addObject("mensagem", 
					new Mensagem("A disciplina #" + id + " não foi enconstrada no banco!", true));
			
			return mv;
		}
	}

	/*---------------DELETE---------------*/
	@GetMapping("/{id}/remover") /* OK */
	public String removerDisciplina(@PathVariable("id") Long id, RedirectAttributes redirect) {
		try {
			disciplinaService.remover(id);
			redirect.addFlashAttribute("mensagem", new Mensagem("Disicplina #" + id + " foi removida com sucesso!"));

			return "redirect:/admin/disciplinas";
		} catch (RuntimeException e) {
			redirect.addFlashAttribute("mensagem",
					new Mensagem("A disciplina #" + id + " não foi enconstrada no banco!", true));

			return "redirect:/admin/disciplinas";
		}
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
