package br.edu.ifrn.portal.dl.controllers.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.portal.dl.exceptions.ResourceNotFountException;
import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.services.PostagemService;
import br.edu.ifrn.portal.dl.services.TecnologiaService;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong> a visualizacao da Postagem<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-04
 * @version 0.1
 */

@Controller
public class ShowPostagemController {

	@Autowired
	private PostagemService postagemService;
	
	@Autowired
	private TecnologiaService tecnologiaService;

	@GetMapping("/post/{titulo}")
	public ModelAndView getPostByTitle(@PathVariable(name = "titulo") String titulo) {
		ModelAndView mv = new ModelAndView("pg-show-postagem");

		titulo = titulo.replace('-', ' ').trim();
		Optional<Postagem> optional = postagemService.obterPorTitulo(titulo);

		if (optional.isPresent()) {
			List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
			
			mv.addObject("principaisTecnologias", pricipaisTecnologias);
			mv.addObject("postagem", optional.get());
			return mv;
		} else {
			throw new ResourceNotFountException();
		}
	}

}
