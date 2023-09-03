package br.edu.ifrn.portal.dl.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe reponsável por trafegar os dados das postagens relacionadas com a 
 * disciplina para a tela de painel de controler
 * @author Erik Vasconcelos
 * @since 2023-07-20
 * @version 1.0 2023-09-03
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoDisciplina {
	
	private String nome;
	private Long quantidade;

}
