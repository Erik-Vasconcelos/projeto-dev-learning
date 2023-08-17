package br.edu.ifrn.portal.dl.controllers.admin;

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

import br.edu.ifrn.portal.dl.dtos.TecnologiaFormDTO;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.services.TecnologiaService;
import br.edu.ifrn.portal.dl.utils.Mensagem;
import br.edu.ifrn.portal.dl.utils.Pesquisa;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong>entidade Tecnologia<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-22
 * @version A0.2
 */

@Controller
@RequestMapping(value = "admin/tecnologias")
public class TecnologiaAdminController {

	@Autowired
	private TecnologiaService tecnologiaService;

	/*---------------READ---------------*/

	@GetMapping /* OK */
	public ModelAndView tecnologiasPaginadas(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<Tecnologia> tecnologiasPaginadas = tecnologiaService.getTecnologiasPaginadas(pageable);
		ModelAndView mv = getIndexTemplate();
		mv.addObject("tecnologias", tecnologiasPaginadas);

		return mv;
	}

	@GetMapping("/pesquisa") /* OK */
	public ModelAndView pesquisarTecnologias(@PageableDefault(page = 0, size = 10) Pageable pageable,
			@Valid Pesquisa pesquisa, BindingResult result) {
		if (result.hasErrors()) {
			return getIndexComDados();

		} else {
			Page<Tecnologia> tecnologiasPaginadas = tecnologiaService
					.getTecnologiasPorNomePaginadas(pesquisa.getValor(), pageable);
			ModelAndView mv = getIndexTemplate();
			mv.addObject("tecnologias", tecnologiasPaginadas);

			return mv;
		}
	}

	@GetMapping("/{id}/editar") /* OK */
	public ModelAndView getPaginaEdicao(@PathVariable("id") Long id, TecnologiaFormDTO tecnologiaDTO,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {

		Optional<Tecnologia> optional = tecnologiaService.obterPorId(id);
		if (optional.isPresent()) {

			Tecnologia tecnologia = optional.get();
			tecnologiaDTO.fromTecnologiaDTO(tecnologia);
			Page<Tecnologia> pageTecnologias = tecnologiaService.getTecnologiasPaginadas(pageable);
			ModelAndView mv = new ModelAndView("pg-edit-admin-tecnologias");
			mv.addObject("tecnologias", pageTecnologias);
			mv.addObject("id", tecnologia.getId());

			return mv;

		} else {
			ModelAndView mv = tecnologiasPaginadas(pageable);
			mv.addObject("mensagem", new Mensagem("A tecnologia #" + id + " não foi encontrada no banco!", true));

			return mv;
		}
	}

	/*---------------CREATE---------------*/

	@PostMapping(value = { "/salvar" }) /* OK */
	public ModelAndView criar(@Valid TecnologiaFormDTO tecnologiaDTO, BindingResult result,
			RedirectAttributes redirect) {

		ModelAndView mv = getIndexComDados();
		if (result.hasErrors()) {
			mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));

			return mv;
		} else {
			if (tecnologiaService.nameExists(tecnologiaDTO.getNome())) {
				mv.addObject("mensagem", new Mensagem("O nome informado já existe no banco de dados!", true));
				return mv;
			}

			Tecnologia tecnologia = tecnologiaDTO.toTecnologia();

			tecnologiaService.salvar(tecnologia);
			redirect.addFlashAttribute("mensagem", new Mensagem("Tecnologia inserida com sucesso!"));

			return new ModelAndView("redirect:/admin/tecnologias");
		}
	}

	/*---------------UPDATE---------------*/

	@PostMapping(value = { "/{id}" })
	public ModelAndView editar(@PathVariable Long id, @Valid TecnologiaFormDTO tecnologiaDTO, BindingResult result,
			RedirectAttributes redirect) {

		ModelAndView mv = getIndexComDados();
		if (result.hasErrors()) {
			mv = getEditComDados();
			mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));

			return mv;
		} else {
			Optional<Tecnologia> optional = tecnologiaService.obterPorId(id);
			if (optional.isPresent()) {
				Tecnologia tecnologia = tecnologiaDTO.configAttibutes(optional.get());

				if (tecnologiaService.nameIsDuplicate(id, tecnologia.getNome())) {
					mv = getEditComDados();
					mv.addObject("mensagem", new Mensagem("O nome informado já existe no banco de dados!", true));
					return mv;
				}

				tecnologiaService.salvar(tecnologia);

				redirect.addFlashAttribute("mensagem",
						new Mensagem("Tecnologia #" + id + " foi atualizada com sucesso!"));
				mv = new ModelAndView("redirect:/admin/tecnologias");

				return mv;
			} else {
				mv = new ModelAndView("redirect:/admin/tecnologias");
				redirect.addFlashAttribute("mensagem",
						new Mensagem("Tecnologia #" + id + " não foi encontrada no banco!", true));
				return mv;
			}
		}
	}

	/*---------------DELETE---------------*/

	@GetMapping("/{id}/remover") /* OK */
	public String removerTecnologia(@PathVariable("id") Long id, RedirectAttributes redirect) {
		try {
			Long qtdPostagensRelacionadas = tecnologiaService.getQuantityRelatedPosts(id);
			boolean temPostagensRelacionadas = qtdPostagensRelacionadas > 0 ? true : false;

			if (!temPostagensRelacionadas) {
				tecnologiaService.remover(id);
				redirect.addFlashAttribute("mensagem",
						new Mensagem("Tecnologia #" + id + " foi removida com sucesso!"));
			} else {
				Optional<Tecnologia> optional = tecnologiaService.obterPorId(id);
				if (optional.isPresent()) {
					Tecnologia disciplina = optional.get();

					redirect.addFlashAttribute("mensagem", new Mensagem("A tecnologia <" + disciplina.getNome()
							+ "> possui " + qtdPostagensRelacionadas
							+ " postagen(s) relacionada(s)! Remova ela(s) antes para poder apagar a tecnologia!",
							true));
				}
			}
			return "redirect:/admin/tecnologias";

		} catch (EmptyResultDataAccessException e) {
			redirect.addFlashAttribute("mensagem",
					new Mensagem("A tecnologia #" + id + " não foi enconstrada no banco!", true));

			return "redirect:/admin/tecnologias";
		}
	}

	/*---------------AXILIARES---------------*/

	@ModelAttribute(value = "tecnologiaFormDTO")
	public TecnologiaFormDTO getNovaTecnologia() {
		return new TecnologiaFormDTO();
	}

	@ModelAttribute(value = "pesquisa")
	public Pesquisa getPesquisaModel() {
		return new Pesquisa();
	}

	private ModelAndView getIndexComDados() {
		ModelAndView mv = getIndexTemplate();
		Page<Tecnologia> pageTecnologias = tecnologiaService
				.getTecnologiasPaginadas(PageRequest.of(0, 10, Sort.by("id")));
		mv.addObject("tecnologias", pageTecnologias);

		return mv;
	}

	private ModelAndView getIndexTemplate() {
		ModelAndView mv = new ModelAndView("pg-admin-tecnologias");
		return mv;
	}

	private ModelAndView getEditComDados() {
		ModelAndView mv = new ModelAndView("pg-edit-admin-tecnologias");
		Page<Tecnologia> pageTecnologias = tecnologiaService
				.getTecnologiasPaginadas(PageRequest.of(0, 10, Sort.by("id")));
		mv.addObject("tecnologias", pageTecnologias);

		return mv;
	}

}
