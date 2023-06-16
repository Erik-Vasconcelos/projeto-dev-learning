package br.edu.ifrn.portal.dl.utils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Classe auxiliar para representar o objeto de pesquisa nas páginas
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-16
 * @version A0.1
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Pesquisa {
	
	@NonNull
	@NotBlank(message = "O valor da pesquisa não pode ser em branco!")
	@Size(min = 3, message = "Insira no mínimo {min} caracteres para a pesquisa")
	private String valor;
}
