package br.edu.ifrn.portal.dl.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.services.PostagemService;

@Controller
public class IndexController {
	
	@Autowired
	private PostagemService postagemService;
	
	private static final int REGISTROS_POR_PAGINA = 2;

	private static final int PAGINA_PADRAO = 0;

	@GetMapping
	public ModelAndView getIndex(@PageableDefault(page = PAGINA_PADRAO, size = REGISTROS_POR_PAGINA) Pageable pageable) {
		ModelAndView mv = new ModelAndView("index");
		Page<Postagem> postagensPaginadas = postagemService.getPostagensPaginadas(pageable);
		mv.addObject("listaPostagens", postagensPaginadas);
		
		return mv;
	}
	
}
