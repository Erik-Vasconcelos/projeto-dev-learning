package br.edu.ifrn.portal.dl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe de erro personalizado para um recurso não encontrado
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-11
 * @version 0.1
 */


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFountException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Esse recurso não foi encontrado!";
	}

}
