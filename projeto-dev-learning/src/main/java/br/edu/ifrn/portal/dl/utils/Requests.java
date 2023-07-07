package br.edu.ifrn.portal.dl.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe do objeto para indentificar o tipo de acao que precisa ser executada ao clicar em um botao de submit.
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-01
 * @version A0.1
 */

@Getter
@Setter
public class Requests {
	
	private String acao;
	
	private Long idItem;

}
