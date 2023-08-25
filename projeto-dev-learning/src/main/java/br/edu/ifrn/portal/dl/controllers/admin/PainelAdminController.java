package br.edu.ifrn.portal.dl.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.services.PostagemService;
import br.edu.ifrn.portal.dl.services.TecnologiaService;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong>entidade Disciplina<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-17
 * @version A0.1
 */

@Controller
@RequestMapping(value = "/admin")
public class PainelAdminController {

	@Autowired
	private PostagemService postagemService;
	
	@Autowired
	private TecnologiaService tecnologiaService;

	@GetMapping
	public ModelAndView getPaginaPainel() {
		ModelAndView mv = new ModelAndView("pg-painel-admin");
		List<Tecnologia> pricipaisTecnologias = tecnologiaService.getPricipaisTecnologias();
		
		mv.addObject("infoPostagens", postagemService.countPostsByType());
		mv.addObject("infoDisciplinas", postagemService.countPostByDisciplinas());
		mv.addObject("principaisTecnologias", pricipaisTecnologias);
		
		return mv;
	}

}
