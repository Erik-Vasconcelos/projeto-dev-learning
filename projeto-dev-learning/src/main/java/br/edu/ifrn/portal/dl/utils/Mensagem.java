package br.edu.ifrn.portal.dl.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Classe reponsável tranportar a mensagem e tipo dela para o 
 * front-end para ser tratado como uma informação ou erro.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-17
 * @version A0.1
 */

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mensagem {

	@NonNull
	private String msg;
	
	private boolean isError = false;

}
