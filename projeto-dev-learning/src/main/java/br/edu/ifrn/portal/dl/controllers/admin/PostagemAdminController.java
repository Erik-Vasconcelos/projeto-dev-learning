package br.edu.ifrn.portal.dl.controllers.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import br.edu.ifrn.portal.dl.dtos.GerenciadorDTO;
import br.edu.ifrn.portal.dl.dtos.PostagemFormDTO;
import br.edu.ifrn.portal.dl.models.Gerenciador;
import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.models.enuns.TipoGerencidor;
import br.edu.ifrn.portal.dl.models.enuns.TipoPostagem;
import br.edu.ifrn.portal.dl.services.DisciplinaService;
import br.edu.ifrn.portal.dl.services.GerenciadorService;
import br.edu.ifrn.portal.dl.services.PostagemService;
import br.edu.ifrn.portal.dl.services.TecnologiaService;
import br.edu.ifrn.portal.dl.utils.ConversorImagem;
import br.edu.ifrn.portal.dl.utils.Mensagem;
import br.edu.ifrn.portal.dl.utils.Pesquisa;
import br.edu.ifrn.portal.dl.utils.UtilPageable;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong>entidade Postagem<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-28
 * @version 1.0 2023-09-03
 */

//@SessionAttributes
@Controller
@RequestMapping(value = "admin/postagens")
public class PostagemAdminController {

	@Autowired
	private GerenciadorService gerenciadorService;

	@Autowired
	private PostagemService postagemService;

	@Autowired
	private DisciplinaService disciplinaService;

	@Autowired
	private TecnologiaService tecnologiaService;

	private static final int REGISTROS_POR_PAGINA = 10;

	private static final int PAGINA_PADRAO = 0;

	/*---------------READ---------------*/

	@GetMapping /* OK */
	public ModelAndView postagensPaginadas(
			@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable) {

		pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);

		Page<Postagem> postagensPaginadas = carregarListaPostagens(pageable);
		ModelAndView mv = getIndexTemplate();
		mv.addObject("listaPostagens", postagensPaginadas);
		mv.addObject("tipoPostagem", TipoPostagem.values());
		mv.addObject("listaDisciplinas", disciplinaService.getListDisciplinas());
		mv.addObject("listaTecnologias", tecnologiaService.getListTecnologias());

		return mv;
	}

	@GetMapping("/pesquisa") /* OK */
	public ModelAndView pesquisarPostagens(
			@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable,
			@Valid Pesquisa pesquisa, BindingResult result) {
		pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);

		if (result.hasErrors()) {
			return getIndexComDados();

		} else {
			Page<Postagem> postagensPaginadas = postagemService.getPostagensPorTituloPaginadas(pesquisa.getValor(),
					pageable);
			ModelAndView mv = postagensPaginadas(pageable);
			mv.addObject("listaPostagens", postagensPaginadas);
			mv.addObject("tipoPostagem", TipoPostagem.values());
			mv.addObject("listaDisciplinas", disciplinaService.getListDisciplinas());
			mv.addObject("listaTecnologias", tecnologiaService.getListTecnologias());

			return mv;
		}
	}

	@GetMapping("/{id}/editar") /* OK */
	public ModelAndView getPaginaEdicao(@PathVariable("id") Long id, PostagemFormDTO postagemDTO,
			@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable) {

		pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);

		Optional<Postagem> optional = postagemService.obterPorId(id);
		if (optional.isPresent()) {
			Postagem postagem = optional.get();
			postagemDTO.fromPostagemDTO(postagem);

			if (postagem.getTecnologias().size() > 0) {
				String tecnologiasJson = new Gson().toJson(postagem.getTecnologias()).replace("[", "").replace("]", "");
				postagemDTO.setTecnologiaTemp(tecnologiasJson);
			}

			ModelAndView mv = new ModelAndView("pg-edit-admin-postagens");
			mv.addObject("id", postagem.getId());
			configDadosDaTela(mv);

			return mv;

		} else {
			ModelAndView mv = postagensPaginadas(pageable);
			mv.addObject("mensagem", new Mensagem("A postagem #" + id + " não foi encontrada no banco!", true));

			return mv;
		}
	}

	/*---------------CREATE---------------*/

	@PostMapping(value = "/salvar")
	public ModelAndView criar(@Valid PostagemFormDTO postagemDTO, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			ModelAndView mv = getIndexComDados();
			if (result.hasErrors()) {
				try {
					if (!postagemDTO.getImagemFile().isEmpty()) {
						postagemDTO.setImagemBanner(ConversorImagem.getImagemEncoded(postagemDTO.getImagemFile()));
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));
			}
			return mv;

		} else {
			try {
				if (!postagemDTO.getImagemFile().isEmpty()) {
					postagemDTO.setImagemBanner(ConversorImagem.getImagemEncoded(postagemDTO.getImagemFile()));
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			if (postagemService.titleExists(postagemDTO.getTitulo())) {
				ModelAndView mv = getIndexComDados();

				mv.addObject("mensagem", new Mensagem("O título informado já existe no banco de dados!", true));
				return mv;

			} else {
				Tecnologia[] tecnologiasRecebidas = new Gson().fromJson("[" + postagemDTO.getTecnologiaTemp() + "]",
						Tecnologia[].class);

				for (Tecnologia t : tecnologiasRecebidas) {
					Optional<Tecnologia> optional = tecnologiaService.obterPorId(t.getId());
					if (optional.isPresent()) {
						postagemDTO.getTecnologias().add(optional.get());
					}
				}

				SecurityContext context = SecurityContextHolder.getContext();
				if (context != null) {
					Object principal = context.getAuthentication().getPrincipal();

					Long id = 0L;
					if (principal instanceof GerenciadorDTO) {
						id = ((GerenciadorDTO) principal).getId();
					}

					Optional<Gerenciador> autor = gerenciadorService.obterPorId(id);

					if (autor.isPresent()) {
						postagemDTO.setAutor(autor.get());
					}

				}

				Postagem postagem = postagemDTO.toPostagem();

				if (postagem.getImagem() == null) {
					postagem.setImagem("");
				}

				postagem.setDataPostagem(LocalDate.now());
				postagemService.salvar(postagem);
				redirect.addFlashAttribute("mensagem", new Mensagem("Postagem inserida com sucesso!"));

				return new ModelAndView("redirect:/admin/postagens");
			}

		}
	}

	/*---------------UPDATE---------------*/

	@PostMapping(value = "/{id}")
	public ModelAndView editar(@PathVariable Long id, @Valid PostagemFormDTO postagemDTO, BindingResult result,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			ModelAndView mv = getEditComDados();

			if (postagemDTO.getImagemFile() != null) {
				if (!postagemDTO.getImagemFile().isEmpty()) {
					postagemDTO.setImagemBanner(ConversorImagem.getImagemEncoded(postagemDTO.getImagemFile()));
				}
			}
			mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));
			return mv;

		} else {
			Optional<Postagem> optional = postagemService.obterPorId(id);
			if (optional.isPresent()) {

				Postagem postagem = postagemDTO.configAttibutes(optional.get());
				postagem.setAutor(optional.get().getAutor());
				postagem.setDataPostagem(optional.get().getDataPostagem());

				if (postagemService.nameIsDuplicate(id, postagem.getTitulo())) {
					ModelAndView mv = getEditComDados();
					mv.addObject("mensagem", new Mensagem("O título informado já existe no banco de dados!", true));
					return mv;

				} else {
					carregarTecnologiasDoBanco(postagemDTO);

					postagem.setTecnologias(postagemDTO.getTecnologias());

					postagemService.salvar(postagem);
					redirect.addFlashAttribute("mensagem",
							new Mensagem("Postagem #" + id + " foi atualizada com sucesso!"));

					return new ModelAndView("redirect:/admin/postagens");
				}

			} else {
				ModelAndView mv = new ModelAndView("redirect:/admin/postagens");
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
		return new PostagemFormDTO();
	}

	@ModelAttribute(value = "pesquisa")
	public Pesquisa getPesquisaModel() {
		return new Pesquisa();
	}

	private ModelAndView getIndexComDados() {
		ModelAndView mv = getIndexTemplate();
		configDadosDaTela(mv);

		return mv;
	}

	private ModelAndView getIndexTemplate() {
		ModelAndView mv = new ModelAndView("pg-admin-postagens");
		List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
		mv.addObject("principaisTecnologias", pricipaisTecnologias);
		
		return mv;
	}

	private ModelAndView getEditComDados() {
		ModelAndView mv = new ModelAndView("pg-edit-admin-postagens");
		configDadosDaTela(mv);

		return mv;
	}

	private void configDadosDaTela(ModelAndView modelAndView) {
		Page<Postagem> postagensPaginadas = carregarListaPostagens(
				PageRequest.of(PAGINA_PADRAO, REGISTROS_POR_PAGINA, Sort.by("id")));
		
		modelAndView.addObject("listaPostagens", postagensPaginadas);
		modelAndView.addObject("tipoPostagem", TipoPostagem.values());
		modelAndView.addObject("listaDisciplinas", disciplinaService.getListDisciplinas());
		modelAndView.addObject("listaTecnologias", tecnologiaService.getListTecnologias());
		modelAndView.addObject("principaisTecnologias", tecnologiaService.getPricipaisTecnologias());
	}

	private void carregarTecnologiasDoBanco(PostagemFormDTO postagemFormDTO) {
		Tecnologia[] tecnologiasRecebidas = new Gson().fromJson("[" + postagemFormDTO.getTecnologiaTemp() + "]",
				Tecnologia[].class);

		Optional<Tecnologia> optionalTecnologia = Optional.of(new Tecnologia());

		for (Tecnologia t : tecnologiasRecebidas) {
			optionalTecnologia = tecnologiaService.obterPorId(t.getId());
			// Se a tecnologia existir no banco
			if (optionalTecnologia.isPresent()) {
				postagemFormDTO.getTecnologias().add(optionalTecnologia.get());
			}
		}
	}

	private Page<Postagem> carregarListaPostagens(Pageable pageable) {
		// Busca as postagens que o gerenciador tem acesso de acordo com o seu tipo:
		// ADMIN ou ESCRITOR

		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {

			Object principal = context.getAuthentication().getPrincipal();

			GerenciadorDTO autor = null;
			if (principal instanceof GerenciadorDTO) {
				autor = ((GerenciadorDTO) principal);
			}

			if (autor != null) {
				Page<Postagem> postagensPaginadas;

				TipoGerencidor tipo = autor.getTipoGerenciador();
				if (tipo.equals(TipoGerencidor.ADMIN_MASTER) || tipo.equals(TipoGerencidor.ADMIN)) {
					postagensPaginadas = postagemService.getPostagensPaginadasOrderData(pageable);
				} else {
					postagensPaginadas = postagemService.getPostagensPorAutorOrderByData(autor.getId(), pageable);
				}

				return postagensPaginadas;
			}

		}

		return new PageImpl<>(new ArrayList<Postagem>());
	}

}
