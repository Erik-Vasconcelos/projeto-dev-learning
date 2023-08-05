package br.edu.ifrn.portal.dl.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controlador do recurso de erro personalizado
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-04
 * @version 0.1
 */

@Controller
public class MyErrorController implements ErrorController {

	@GetMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		ModelAndView mv = new ModelAndView("error");

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				mv.addObject("errorType", "Error-404: Página não encontrada!");
				mv.addObject("information",
						"Esta página não foi encontrada no servidor! Provavelmente ela não existe ou houve algum erro na URL.");
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				mv.addObject("errorType", "Error-403: Acesso negado!");
				mv.addObject("information",
						"Você não tem permissão para acessar esta página!");
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				mv.addObject("errorType", "Error-500: Erro de processamento no servidor!");
				mv.addObject("information",
						"Ocorreu um erro inesperado ao tentar executar a operação. Por favor tente novamente mais tarde!");
			}
		}else {
			mv.addObject("errorType", "Error: Algum erro inesperado!");
			mv.addObject("information",
						"Ocorreu um erro ao tentar acessar este recurso. Por favor tente novamente mais tarde!");
		}
		return mv;
	}

}
