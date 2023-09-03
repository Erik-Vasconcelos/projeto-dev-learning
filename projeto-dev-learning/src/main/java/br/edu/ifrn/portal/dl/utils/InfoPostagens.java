package br.edu.ifrn.portal.dl.utils;

import br.edu.ifrn.portal.dl.models.enuns.TipoPostagem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* Classe responsável por trafegar os dados das postagens para o painel de controler
*
* @author Erik Vasconcelos
* @since 2023-07-20
* @version 1.0 2023-09-03
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoPostagens {
	
	private TipoPostagem tipo;
	private Long quantidade;

	public InfoPostagens(Integer tipo, Long quantidade) {
		this.tipo = TipoPostagem.toEnum(tipo);
		this.quantidade = quantidade;
	}
	
}
