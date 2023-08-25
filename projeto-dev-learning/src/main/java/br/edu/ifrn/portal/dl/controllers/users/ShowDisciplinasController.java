package br.edu.ifrn.portal.dl.controllers.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.portal.dl.exceptions.ResourceNotFountException;
import br.edu.ifrn.portal.dl.models.Disciplina;
import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.services.DisciplinaService;
import br.edu.ifrn.portal.dl.services.PostagemService;
import br.edu.ifrn.portal.dl.services.TecnologiaService;
import br.edu.ifrn.portal.dl.utils.DisciplinaPosts;
import br.edu.ifrn.portal.dl.utils.UtilPageable;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong> a visualizacao das disciplinas<strong> abordadas no
 * blog.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-15
 * @version 0.1
 */

@Controller
@RequestMapping("/disciplinas")
public class ShowDisciplinasController {

	@Autowired
	private DisciplinaService disciplinaService;
	
	@Autowired
	private PostagemService postagemService;
	
	@Autowired
	private TecnologiaService tecnologiaService;

	private static final int REGISTROS_POR_PAGINA = 8;

	private static final int PAGINA_PADRAO = 0;

	@GetMapping
	public ModelAndView getPaginaDisciplinas(
			@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable) {
		ModelAndView mv = new ModelAndView("pg-show-disciplinas");
		pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);

		Page<DisciplinaPosts> disciplinaPostsPaginadas = getDisciplinasAndQtdPostsRelated(pageable);
		List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
		
		mv.addObject("listaDisciplinasPosts", disciplinaPostsPaginadas);
		mv.addObject("principaisTecnologias", pricipaisTecnologias);
		return mv;
	}

	@GetMapping("/{nome}")
	public ModelAndView getPaginaDisciplina(@PathVariable("nome") String nome,
			@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable) {
		ModelAndView mv = new ModelAndView("pg-show-disciplina");
		nome =  nome.replace('-', ' ').trim();
		
		Optional<Disciplina> optional = disciplinaService.obterDisciplinaPorNome(nome);
		
		if(optional.isPresent()) {
			pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);
			Disciplina disciplina = optional.get();
			Page<Postagem> postagensPaginadas = postagemService.getPostagensPorDisciplina(disciplina.getId(), pageable);
			List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
			
			mv.addObject("disciplina", disciplina);
			mv.addObject("listaPostagens", postagensPaginadas);
			mv.addObject("principaisTecnologias", pricipaisTecnologias);
			
			return mv;
		}else {
			throw new ResourceNotFountException();
		}
	}

	private Page<DisciplinaPosts> getDisciplinasAndQtdPostsRelated(Pageable pageable) {
		Page<Disciplina> disciplinasPaginadas = disciplinaService.getDisciplinasPaginadasOrdenadasPorNome(pageable);

		Page<DisciplinaPosts> disciplinaPostsPaginadas = disciplinasPaginadas.map(d -> {
			long quantidade = disciplinaService.getQuantityRelatedPosts(d.getId());
			return new DisciplinaPosts(quantidade, d);
		});

		return disciplinaPostsPaginadas;
	}

}
