package br.edu.ifrn.portal.dl.controllers.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.portal.dl.exceptions.ResourceNotFountException;
import br.edu.ifrn.portal.dl.models.EmailDuvida;
import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.models.enuns.StatusEmail;
import br.edu.ifrn.portal.dl.services.EmailService;
import br.edu.ifrn.portal.dl.services.PostagemService;
import br.edu.ifrn.portal.dl.services.TecnologiaService;

/**
 * Classe responsável por interceptar e gerenciar o fluxo de requisições
 * relacionados à <strong> a visualizacao da Postagem<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-04
 * @version 1.0 2023-09-03
 */

@Controller
@RequestMapping("/post")
public class ShowPostagemController {

	@Autowired
	private PostagemService postagemService;

	@Autowired
	private TecnologiaService tecnologiaService;

	@Autowired
	private EmailService emailService;

	@GetMapping("/{titulo}")
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

	@PostMapping("/enviar-email")
	public ResponseEntity<String> enviarEmail(@RequestBody EmailDuvida email) {
		emailService.sendEmail(email);

		if (email.getStatus() == StatusEmail.ENVIADO) {
			return new ResponseEntity<String>("Email enviado com sucesso", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("Não foi possível enviar o e-mail", HttpStatus.SERVICE_UNAVAILABLE);

		}
	}

}
