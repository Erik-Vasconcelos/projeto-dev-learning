package br.edu.ifrn.portal.dl.utils;

import br.edu.ifrn.portal.dl.models.Disciplina;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe responsável por encapsular a <strong>disciplina<strong> e a
 * quantidade de postagens relacionadas, sendo isto apresentado na página
 * das disciplinas.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-17
 * @version 0.1
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DisciplinaPosts {
	private long quantidade;
	
	private Disciplina disciplina;
	
}
