package br.edu.ifrn.portal.dl.controllers;

import java.util.ArrayList;
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

import br.edu.ifrn.portal.dl.dtos.PostagemFormDTO;
import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.models.enuns.TipoPostagem;
import br.edu.ifrn.portal.dl.services.DisciplinaService;
import br.edu.ifrn.portal.dl.services.PostagemService;
import br.edu.ifrn.portal.dl.services.TecnologiaService;
import br.edu.ifrn.portal.dl.utils.ConversorImagem;
import br.edu.ifrn.portal.dl.utils.Mensagem;
import br.edu.ifrn.portal.dl.utils.Pesquisa;
import br.edu.ifrn.portal.dl.utils.Requests;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong>entidade Postagem<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-13
 * @version A0.2
 */

//@SessionAttributes
@Controller
@RequestMapping(value = "admin/postagens")
public class PostagemAdminController {

	@Autowired
	private PostagemService postagemService;

	@Autowired
	private DisciplinaService disciplinaService;

	@Autowired
	private TecnologiaService tecnologiaService;

	private PostagemFormDTO postagemFormDTO = new PostagemFormDTO();

	/*---------------READ---------------*/

	@GetMapping /* OK */
	public ModelAndView postagensPaginadas(PostagemFormDTO postagemFormDTO, Tecnologia tecnologia,
			@PageableDefault(page = 0, size = 10) Pageable pageable, Mensagem mensagem) {
		Page<Postagem> postagensPaginadas = postagemService.getPostagensPaginadas(pageable);
		ModelAndView mv = getIndexTemplate();
		mv.addObject("listaPostagens", postagensPaginadas);

		mv.addObject("tipoPostagem", TipoPostagem.values());
		mv.addObject("listaDisciplinas", disciplinaService.getListDisciplinas());
		mv.addObject("listaTecnologias", tecnologiaService.getListTecnologias());
		// Adicionando a lista de tecnologias relacionadas na postagem
		// mv.addObject("listaTecnologiasPostagem", this.listaTecnologiasPostagem);
		mv.addObject("listaTecnologiasPostagem", this.postagemFormDTO.getTecnologias());
		// Objeto modelo do form
		mv.addObject("postagemFormDTO", postagemFormDTO);

		if(mensagem != null) {
			mv.addObject("mensagem", mensagem);
		}
		
		return mv;
	}
	/*
	 * @GetMapping public ModelAndView postagensPaginadas(@PageableDefault(page = 0,
	 * size = 10) Pageable pageable) { Page<Postagem> postagensPaginadas =
	 * postagemService.getPostagensPaginadas(pageable); ModelAndView mv =
	 * getIndexTemplate(); mv.addObject("postagens", postagensPaginadas);
	 * mv.addObject("tipos", TipoPostagem.values()); mv.addObject("disciplinas",
	 * disciplinaService.getListDisciplinas());
	 * 
	 * mv.addObject("tecnologias", tecnologiaService.getListTecnologias());
	 * 
	 * return mv; }
	 */

	@GetMapping("/pesquisa") /* OK */
	public ModelAndView pesquisarPostagens(@PageableDefault(page = 0, size = 10) Pageable pageable,
			@Valid Pesquisa pesquisa, BindingResult result) {
		if (result.hasErrors()) {
			return getIndexComDados();

		} else {
			Page<Postagem> postagensPaginadas = postagemService.getPostagensPorTituloPaginadas(pesquisa.getValor(),
					pageable);
			ModelAndView mv = getIndexTemplate();
			mv.addObject("postagens", postagensPaginadas);

			return mv;
		}
	}

	/*
	 * @GetMapping("/{id}/editar") public ModelAndView
	 * getPaginaEdicao(@PathVariable("id") Long id, PostagemFormDTO postagemDTO,
	 * 
	 * @PageableDefault(page = 0, size = 10) Pageable pageable) {
	 * 
	 * Optional<Postagem> optional = postagemService.obterPorId(id); if
	 * (optional.isPresent()) {
	 * 
	 * Postagem postagem = optional.get(); postagemDTO.fromPostagemDTO(postagem);
	 * Page<Postagem> pagePostagens =
	 * postagemService.getPostagensPaginadas(pageable); ModelAndView mv = new
	 * ModelAndView("pg-edit-admin-postagens"); mv.addObject("postagens",
	 * pagePostagens); mv.addObject("id", postagem.getId());
	 * 
	 * return mv;
	 * 
	 * } else { ModelAndView mv = postagensPaginadas(pageable);
	 * mv.addObject("mensagem", new Mensagem("A postagem #" + id +
	 * " não foi encontrada no banco!", true));
	 * 
	 * return mv; } }
	 */

	/*---------------CREATE---------------*/
	
	private void teste(@Valid PostagemFormDTO postagemDTO, BindingResult result) {
		if (result.hasErrors()) {
			
		}
	}

	@PostMapping(value = "/salvar")
	public ModelAndView criar(Requests request, @Valid PostagemFormDTO postagemDTO, BindingResult result,
			Tecnologia tecnologia, RedirectAttributes redirect) {

		if (result.hasErrors()) {
			 return postagensPaginadas(postagemDTO, tecnologia, null, new Mensagem("Verifique os campos de entrada!", true));
		} else {

			if (!postagemFormDTO.getImagemFile().isEmpty()) {
				postagemDTO.setImagemBanner(ConversorImagem.getImagemEncoded(postagemDTO.getImagemFile()));
			}

			if (request.getAcao().equals("addTecnologia")) {
				if (!this.postagemFormDTO.getTecnologias().contains(tecnologia)) {
					postagemDTO.getTecnologias().add(tecnologia);
					this.postagemFormDTO = postagemDTO;
				}

			} else if (request.getAcao().equals("removeTecnologia")) {
				Optional<Tecnologia> optionalTecnologia = this.postagemFormDTO.getTecnologias().stream()
						.filter(t -> t.getId().equals(request.getIdItem())).findFirst();

				if (optionalTecnologia.isPresent()) {
					this.postagemFormDTO.getTecnologias().remove(optionalTecnologia.get());
				}

			} else if (request.getAcao().equals("salvarPostagem")) {

				if (postagemService.titleExists(postagemDTO.getTitulo())) {
					/*
					 * mv.addObject("mensagem", new
					 * Mensagem("O título informado já existe no banco de dados!", true)); return
					 * mv;
					 */
				} else {

					Postagem postagem = postagemDTO.toPostagem();

					if (postagem.getImagem() == null) {
						postagem.setImagem("");
					}

					postagemService.salvar(postagem);
					redirect.addFlashAttribute("mensagem", new Mensagem("Postagem inserida com sucesso!"));

					return new ModelAndView("redirect:/admin/postagens");

					// return postagensPaginadas(new PostagemFormDTO(), new Tecnologia(), null);
				}

			}

			return postagensPaginadas(postagemDTO, tecnologia, null, null);

		}
		/*
		 * ModelAndView mv = getIndexComDados(); if (result.hasErrors()) {
		 * mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!",
		 * true));
		 * 
		 * return mv; } else { if (postagemService.nameExists(postagemDTO.getTitulo()))
		 * { mv.addObject("mensagem", new
		 * Mensagem("O título informado já existe no banco de dados!", true)); return
		 * mv; }
		 * 
		 * Postagem postagem = postagemDTO.toPostagem();
		 * 
		 * if (postagem.getImagem() == null) { postagem.setImagem(""); }
		 * 
		 * postagemService.salvar(postagem); redirect.addFlashAttribute("mensagem", new
		 * Mensagem("Postagem inserida com sucesso!"));
		 * 
		 * return new ModelAndView("redirect:/admin/postagens"); }
		 */
	}

	/*---------------UPDATE---------------*/

	@PostMapping(value = "/{id}")
	public ModelAndView editar(@PathVariable Long id, @Valid PostagemFormDTO postagemDTO, BindingResult result,
			RedirectAttributes redirect) {

		ModelAndView mv = getIndexComDados();
		if (result.hasErrors()) {
			mv = getEditComDados();
			mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));
			postagemDTO.setImagemBanner(postagemService.getImage(id));

			return mv;
		} else {
			Optional<Postagem> optional = postagemService.obterPorId(id);
			if (optional.isPresent()) {
				Postagem postagem = postagemDTO.configAttibutes(optional.get());

				if (postagem.getImagem() == null || postagem.getImagem().isBlank()) {
					postagem.setImagem(postagemService.getImage(id));
				}

				if (postagemService.nameIsDuplicate(id, postagem.getTitulo())) {
					mv = getEditComDados();
					mv.addObject("mensagem", new Mensagem("O nome informado já existe no banco de dados!", true));
					postagemDTO.setImagemBanner(postagemService.getImage(id));
					return mv;
				}

				postagemService.salvar(postagem);

				redirect.addFlashAttribute("mensagem",
						new Mensagem("Postagem #" + id + " foi atualizada com sucesso!"));
				mv = new ModelAndView("redirect:/admin/postagens");

				return mv;
			} else {
				mv = new ModelAndView("redirect:/admin/postagens");
				redirect.addFlashAttribute("mensagem",
						new Mensagem("Postagem #" + id + " não foi encontrada no banco!", true));
				return mv;
			}
		}
	}

	/*---------------DELETE---------------*/

	@GetMapping("/{id}/remover") /* OK */
	public String removerPostagem(@PathVariable("id") Long id, RedirectAttributes redirect) {
		try {
			postagemService.remover(id);
			redirect.addFlashAttribute("mensagem", new Mensagem("Postagem #" + id + " foi removida com sucesso!"));

			return "redirect:/admin/postagens";
		} catch (EmptyResultDataAccessException e) {
			redirect.addFlashAttribute("mensagem",
					new Mensagem("A postagem #" + id + " não foi enconstrada no banco!", true));

			return "redirect:/admin/postagens";
		}
	}

	/*---------------AXILIARES---------------*/

	@ModelAttribute(value = "postagemFormDTO")
	public PostagemFormDTO getNovaPostagem() {
		return this.postagemFormDTO;
	}

	@ModelAttribute(value = "pesquisa")
	public Pesquisa getPesquisaModel() {
		return new Pesquisa();
	}

	private ModelAndView getIndexComDados() {
		ModelAndView mv = getIndexTemplate();
		Page<Postagem> pagePostagens = postagemService.getPostagensPaginadas(PageRequest.of(0, 10, Sort.by("id")));
		mv.addObject("postagens", pagePostagens);

		return mv;
	}

	private ModelAndView getIndexTemplate() {
		ModelAndView mv = new ModelAndView("pg-admin-postagens");
		return mv;
	}

	private ModelAndView getEditComDados() {
		ModelAndView mv = new ModelAndView("pg-edit-admin-postagens");
		Page<Postagem> pagePostagens = postagemService.getPostagensPaginadas(PageRequest.of(0, 10, Sort.by("id")));
		mv.addObject("postagens", pagePostagens);

		return mv;
	}

}
