package br.edu.ifrn.portal.dl.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong> a visualizacao das disciplinas<strong> abordadas no blog.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-15
 * @version 0.1
 */

@Controller
@RequestMapping("/disciplinas")
public class ShowDisciplinasController {

	@GetMapping
	public ModelAndView getPaginaDisciplinas() {
		ModelAndView mv = new ModelAndView("pg-show-disciplinas");
		return mv;
	}
}
