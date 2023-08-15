package br.edu.ifrn.portal.dl.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe responsável por encapsular a <strong>tecnologia<strong> e às
 * informações de postagens relacionadas, sendo isto apresentado na página
 * inicial.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-14
 * @version 0.1
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostsTecnologia {

	private Long idTecnologia;
	
	private String nomeTecnologia;

	private Long quantidade;
}
