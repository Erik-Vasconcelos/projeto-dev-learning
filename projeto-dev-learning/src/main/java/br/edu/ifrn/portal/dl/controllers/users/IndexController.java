package br.edu.ifrn.portal.dl.controllers.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.services.PostagemService;
import br.edu.ifrn.portal.dl.services.TecnologiaService;
import br.edu.ifrn.portal.dl.utils.PostsTecnologia;
import br.edu.ifrn.portal.dl.utils.UtilPageable;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong>página incial<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-03
 * @version 1.0 2023-09-03
 */

@Controller
public class IndexController {
	
	@Autowired
	private PostagemService postagemService;
	
	@Autowired
	private TecnologiaService tecnologiaService;
	
	private static final int REGISTROS_POR_PAGINA = 10;

	private static final int PAGINA_PADRAO = 0;

	@GetMapping
	public ModelAndView getIndex(@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable) {
		ModelAndView mv = new ModelAndView("index");
		pageable = UtilPageable.verifySizePageable(REGISTROS_POR_PAGINA, pageable);
		
		Page<Postagem> postagensPaginadas = postagemService.getPostagensPaginadasOrderData(pageable);
		List<PostsTecnologia> postPorTecnologias = tecnologiaService.getPostsPorTecnologia();
		List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
		
		mv.addObject("listaPostagens", postagensPaginadas);
		mv.addObject("listaPostsPorTecnologia", postPorTecnologias);
		mv.addObject("principaisTecnologias", pricipaisTecnologias);
		
		return mv;
	}
	
}
