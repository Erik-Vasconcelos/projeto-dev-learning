package br.edu.ifrn.portal.dl.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	@GetMapping("/pesquisa") /* OK */
	public ModelAndView pesquisarDisciplinas(@PageableDefault(page = 0, size = 10) Pageable pageable,
			@Valid Pesquisa pesquisa, BindingResult result) {
		if (result.hasErrors()) {
			return getIndexComDados();

		} else {
			Page<Disciplina> disciplinasPaginadas = disciplinaService
					.getDisciplinasPorNomePaginadas(pesquisa.getValor(), pageable);
			ModelAndView mv = getIndexTemplate();
			mv.addObject("disciplinas", disciplinasPaginadas);

			return mv;
		}
	}

	@GetMapping("/{id}/editar") /* OK */
	public ModelAndView getPaginaEdicao(@PathVariable("id") Long id, DisciplinaFormDTO disciplinaDTO,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {

		Optional<Disciplina> optional = disciplinaService.obterPorId(id);
		if (optional.isPresent()) {

			Disciplina disciplina = optional.get();
			disciplinaDTO.fromDisciplinaDTO(disciplina);
			Page<Disciplina> pageDisciplinas = disciplinaService.getDisciplinasPaginadas(pageable);
			ModelAndView mv = new ModelAndView("pg-edit-admin-disciplinas");
			mv.addObject("disciplinas", pageDisciplinas);
			mv.addObject("id", disciplina.getId());

			return mv;

		} else {
			ModelAndView mv = disciplinasPaginadas(pageable);
			mv.addObject("mensagem", new Mensagem("A disciplina #" + id + " não foi encontrada no banco!", true));

			return mv;
		}
	}

	/*---------------CREATE---------------*/

	@PostMapping(value = { "/salvar" }) /* OK */
	public ModelAndView criar(@Valid DisciplinaFormDTO disciplinaDTO, BindingResult result,
			RedirectAttributes redirect) {

		ModelAndView mv = getIndexComDados();
		if (result.hasErrors()) {
			mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));

			return mv;
		} else {
			if (disciplinaService.nameExists(disciplinaDTO.getNome())) {
				mv.addObject("mensagem", new Mensagem("O nome informado já existe no banco de dados!", true));
				return mv;
			}

			Disciplina disciplina = disciplinaDTO.toDisciplina();

			if (disciplina.getImagem() == null) {
				disciplina.setImagem("");
			}

			disciplinaService.salvar(disciplina);
			redirect.addFlashAttribute("mensagem", new Mensagem("Disciplina inserida com sucesso!"));

			return new ModelAndView("redirect:/admin/disciplinas");
		}
	}

	/*---------------UPDATE---------------*/

	@PostMapping(value = { "/{id}" })
	public ModelAndView editar(@PathVariable Long id, @Valid DisciplinaFormDTO disciplinaDTO, BindingResult result,
			RedirectAttributes redirect) {

		ModelAndView mv = getIndexComDados();
		if (result.hasErrors()) {
			mv = getEditComDados();
			mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));
			disciplinaDTO.setImagem(disciplinaService.getImage(id));

			return mv;
		} else {
			Optional<Disciplina> optional = disciplinaService.obterPorId(id);
			if (optional.isPresent()) {
				Disciplina disciplina = disciplinaDTO.configAttibutes(optional.get());

				if (disciplina.getImagem() == null || disciplina.getImagem().isBlank()) {
					disciplina.setImagem(disciplinaService.getImage(id));
				}

				if (disciplinaService.nameIsDuplicate(id, disciplina.getNome())) {
					mv = getEditComDados();
					mv.addObject("mensagem", new Mensagem("O nome informado já existe no banco de dados!", true));
					disciplinaDTO.setImagem(disciplinaService.getImage(id));
					return mv;
				}

				disciplinaService.salvar(disciplina);

				redirect.addFlashAttribute("mensagem",
						new Mensagem("Disciplina #" + id + " foi atualizada com sucesso!"));
				mv = new ModelAndView("redirect:/admin/disciplinas");

				return mv;
			} else {
				mv = new ModelAndView("redirect:/admin/disciplinas");
				redirect.addFlashAttribute("mensagem",
						new Mensagem("Disciplina #" + id + " não foi encontrada no banco!", true));
				return mv;
			}
		}
	}

	/*---------------DELETE---------------*/

	@GetMapping("/{id}/remover") /* OK */
	public String removerDisciplina(@PathVariable("id") Long id, RedirectAttributes redirect) {
		try {
			disciplinaService.remover(id);
			redirect.addFlashAttribute("mensagem", new Mensagem("Disicplina #" + id + " foi removida com sucesso!"));

			return "redirect:/admin/disciplinas";
		} catch (EmptyResultDataAccessException e) {
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

	private ModelAndView getEditComDados() {
		ModelAndView mv = new ModelAndView("pg-edit-admin-disciplinas");
		Page<Disciplina> pageDisciplinas = disciplinaService
				.getDisciplinasPaginadas(PageRequest.of(0, 10, Sort.by("id")));
		mv.addObject("disciplinas", pageDisciplinas);

		return mv;
	}

}
