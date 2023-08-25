package br.edu.ifrn.portal.dl.controllers.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import br.edu.ifrn.portal.dl.dtos.GerenciadorFormDTO;
import br.edu.ifrn.portal.dl.dtos.GerenciadorFormEditDTO;
import br.edu.ifrn.portal.dl.models.Gerenciador;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.models.enuns.TipoGerencidor;
import br.edu.ifrn.portal.dl.services.GerenciadorService;
import br.edu.ifrn.portal.dl.services.RoleService;
import br.edu.ifrn.portal.dl.services.TecnologiaService;
import br.edu.ifrn.portal.dl.utils.Mensagem;
import br.edu.ifrn.portal.dl.utils.Pesquisa;
import br.edu.ifrn.portal.dl.utils.UtilPageable;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong>entidade Gerenciador<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-20
 * @version 0.1
 */

@Controller
@RequestMapping(value = "admin/gerenciadores")
public class GerenciadorAdminController {

	private static final int REGISTROS_POR_PAGINA = 10;

	private static final int PAGINA_PADRAO = 0;

	@Autowired
	private GerenciadorService gerenciadorService;

	@Autowired
	private TecnologiaService tecnologiaService;
	
	@Autowired
	private RoleService roleService;

	/*---------------READ---------------*/

	@GetMapping /* OK */
	public ModelAndView gerenciadoresPaginados(
			@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable) {
		pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);
		ModelAndView mv = getIndexTemplate();
		
		Page<Gerenciador> gerenciadoresPaginados = gerenciadorService.getGerenciadoresPaginados(pageable);
		List<TipoGerencidor> tipoGerenciador = Arrays.stream(TipoGerencidor.values())
				.filter(tp -> tp.getCodigo() != TipoGerencidor.ADMIN_MASTER.getCodigo()).toList();

		mv.addObject("tipoGerenciador", tipoGerenciador);
		mv.addObject("listaGerenciadores", gerenciadoresPaginados);

		return mv;
	}

	@GetMapping("/pesquisa")
	public ModelAndView pesquisarGerenciadores(
			@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable,
			@Valid Pesquisa pesquisa, BindingResult result) {
		if (result.hasErrors()) {
			return getIndexComDados();

		} else {
			pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);

			Page<Gerenciador> gerenciadoresPaginados = gerenciadorService
					.getGerenciadoresPorNomePaginados(pesquisa.getValor(), pageable);
			ModelAndView mv = getIndexTemplate();
			mv.addObject("listaGerenciadores", gerenciadoresPaginados);

			return mv;
		}
	}

	@GetMapping("/{id}/editar")
	public ModelAndView getPaginaEdicao(@PathVariable("id") Long id, GerenciadorFormEditDTO gerenciadorFormEditDTO,
			@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable) {

		pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);

		Optional<Gerenciador> optional = gerenciadorService.obterPorId(id);
		
		if (optional.isPresent()) {
			Gerenciador gerenciador = optional.get();
			
			if(gerenciador.getTipoGerenciador().getCodigo() == TipoGerencidor.ADMIN_MASTER.getCodigo()) {
				ModelAndView mv = gerenciadoresPaginados(pageable);
				mv.addObject("mensagem", new Mensagem("Não é possivel editar o gerenciador #" + id, true));

				return mv;
			}

			gerenciadorFormEditDTO.fromGerenciadorDTO(gerenciador);
			Page<Gerenciador> pageGerenciador = gerenciadorService.getGerenciadoresPaginados(pageable);
			ModelAndView mv = new ModelAndView("pg-edit-admin-gerenciadores");
			
			List<TipoGerencidor> tipoGerenciador = Arrays.stream(TipoGerencidor.values())
					.filter(tp -> tp.getCodigo() != TipoGerencidor.ADMIN_MASTER.getCodigo()).toList();
			List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
			
			mv.addObject("listaGerenciadores", pageGerenciador);
			mv.addObject("id", gerenciador.getId());
			mv.addObject("tipoGerenciador", tipoGerenciador);
			mv.addObject("principaisTecnologias", pricipaisTecnologias);

			return mv;

		} else {
			ModelAndView mv = gerenciadoresPaginados(pageable);
			mv.addObject("mensagem", new Mensagem("O gerenciador #" + id + " não foi encontrada no banco!", true));

			return mv;
		}
	}

	/*---------------CREATE---------------*/

	@PostMapping(value = { "/salvar" })
	public ModelAndView criar(@Valid GerenciadorFormDTO gerenciadorFormDTO, BindingResult result,
			RedirectAttributes redirect) {

		ModelAndView mv = getIndexComDados();
		if (result.hasErrors()) {
			mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));

			return mv;
		} else {
			if (gerenciadorService.nameExists(gerenciadorFormDTO.getNome())) {
				mv.addObject("mensagem", new Mensagem("O nome informado já existe no banco de dados!", true));
				return mv;
			}

			Gerenciador gerenciador = gerenciadorFormDTO.toGerenciador();

			// Configura a lista de roles no gerenciador de acordo com o tipo do gerenciador
			gerenciador.getRoles().addAll(roleService.obterRolesPorTipoGerenciador(gerenciador.getTipoGerenciador()));

			if (gerenciador.getImagem() == null) {
				gerenciador.setImagem("");
			}

			gerenciadorService.salvar(gerenciador);
			redirect.addFlashAttribute("mensagem", new Mensagem("Gerenciador inserido com sucesso!"));

			return new ModelAndView("redirect:/admin/gerenciadores");
		}
	}

	/*---------------UPDATE---------------*/
	
	@PostMapping(value = { "/{id}" })
	public ModelAndView editar(@PathVariable Long id, @Valid GerenciadorFormEditDTO gerenciadorFormEditDTO, BindingResult result,
			RedirectAttributes redirect) {
	
		ModelAndView mv = getIndexComDados();
		if (result.hasErrors()) {
			mv = getEditComDados();
			mv.addObject("mensagem", new Mensagem("Verifique os campos de entrada!", true));
			gerenciadorFormEditDTO.setImagem(gerenciadorService.getImage(id));
	
			return mv;
		} else {
			Optional<Gerenciador> optional = gerenciadorService.obterPorId(id);
			if (optional.isPresent()) {
				Gerenciador gerenciador = gerenciadorFormEditDTO.configAttibutes(optional.get());
	
				if (gerenciador.getImagem() == null || gerenciador.getImagem().isBlank()) {
					gerenciador.setImagem(gerenciadorService.getImage(id));
				}
	
				if (gerenciadorService.nameIsDuplicate(id, gerenciador.getNome())) {
					mv = getEditComDados();
					mv.addObject("mensagem", new Mensagem("O nome informado já existe no banco de dados!", true));
					gerenciadorFormEditDTO.setImagem(gerenciadorService.getImage(id));
					return mv;
				}
				
				gerenciador.getRoles().clear();
				gerenciador.getRoles().addAll(roleService.obterRolesPorTipoGerenciador(gerenciador.getTipoGerenciador()));
	
				gerenciadorService.salvar(gerenciador);
	
				redirect.addFlashAttribute("mensagem",
						new Mensagem("Gerenciador #" + id + " foi atualizado com sucesso!"));
				mv = new ModelAndView("redirect:/admin/gerenciadores");
	
				return mv;
			} else {
				mv = new ModelAndView("redirect:/admin/gerenciadores");
				redirect.addFlashAttribute("mensagem",
						new Mensagem("Gerenciador #" + id + " não foi encontrada no banco!", true));
				return mv;
			}
		}
	}

	/*---------------AXILIARES---------------*/

	@ModelAttribute(value = "gerenciadorFormDTO")
	public GerenciadorFormDTO getNovoGerenciadorForm() {
		return new GerenciadorFormDTO();
	}
	
	@ModelAttribute(value = "gerenciadorFormEditDTO")
	public GerenciadorFormEditDTO getNovoGerenciadorFormEdit() {
		return new GerenciadorFormEditDTO();
	}


	@ModelAttribute(value = "pesquisa")
	public Pesquisa getPesquisaModel() {
		return new Pesquisa();
	}

	private ModelAndView getIndexComDados() {
		ModelAndView mv = getIndexTemplate();
		Page<Gerenciador> pageGerenciador = gerenciadorService
				.getGerenciadoresPaginados(PageRequest.of(PAGINA_PADRAO, REGISTROS_POR_PAGINA, Sort.by("id")));

		List<TipoGerencidor> tipoGerenciador = Arrays.stream(TipoGerencidor.values())
				.filter(tp -> tp.getCodigo() != TipoGerencidor.ADMIN_MASTER.getCodigo()).toList();
		List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
		
		mv.addObject("tipoGerenciador", tipoGerenciador);
		mv.addObject("listaGerenciadores", pageGerenciador);
		mv.addObject("principaisTecnologias", pricipaisTecnologias);

		return mv;
	}

	private ModelAndView getIndexTemplate() {
		ModelAndView mv = new ModelAndView("pg-admin-gerenciadores");
		List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
		mv.addObject("principaisTecnologias", pricipaisTecnologias);
		
		return mv;
	}

	private ModelAndView getEditComDados() {
		ModelAndView mv = new ModelAndView("pg-edit-admin-gerenciadores");
		Page<Gerenciador> pageGerenciador = gerenciadorService
				.getGerenciadoresPaginados(PageRequest.of(PAGINA_PADRAO, REGISTROS_POR_PAGINA, Sort.by("id")));

		List<TipoGerencidor> tipoGerenciador = Arrays.stream(TipoGerencidor.values())
				.filter(tp -> tp.getCodigo() != TipoGerencidor.ADMIN_MASTER.getCodigo()).toList();
		List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
		
		mv.addObject("tipoGerenciador", tipoGerenciador);
		mv.addObject("listaGerenciadores", pageGerenciador);
		mv.addObject("principaisTecnologias", pricipaisTecnologias);

		return mv;
	}

}
