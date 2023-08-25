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
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.portal.dl.exceptions.ResourceNotFountException;
import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.services.PostagemService;
import br.edu.ifrn.portal.dl.services.TecnologiaService;
import br.edu.ifrn.portal.dl.utils.UtilPageable;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong> a visualizacao das postagens de uma
 * tecnologia<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-19
 * @version 0.1
 */

@Controller
public class ShowTecnologiaController {

	@Autowired
	private TecnologiaService tecnologiaService;

	@Autowired
	private PostagemService postagemService;

	private static final int REGISTROS_POR_PAGINA = 10;

	private static final int PAGINA_PADRAO = 0;

	@GetMapping("/posts/tecnologia/{nome}")
	public ModelAndView getPaginaTecnolgia(@PathVariable(name = "nome") String nome,
			@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable) {
		ModelAndView mv = new ModelAndView("pg-show-tecnologia");

		nome = nome.replace('-', ' ');
		Optional<Tecnologia> optional = tecnologiaService.obterPorNome(nome);

		if (optional.isPresent()) {
			pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);
			
			Tecnologia tecnologia = optional.get();
			Page<Postagem> postagensPaginadas = postagemService.getPostagensPorTecnologia(tecnologia.getId(), pageable);
			List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
			
			mv.addObject("tecnologia", tecnologia);
			mv.addObject("listaPostagens", postagensPaginadas);
			mv.addObject("principaisTecnologias", pricipaisTecnologias);

			return mv;
		} else {
			throw new ResourceNotFountException();
		}
	}

}
