package br.edu.ifrn.portal.dl.models.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum do tipo da postagem
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-28
 * @version 1.0 2023-09-03
 */

@AllArgsConstructor
@Getter
public enum TipoPostagem {

	ARTIGO(1, "Artigo"),
	TUTORIAL(2, "Tutorial");

	private int codigo;
	private String descricao;

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static TipoPostagem toEnum(Integer codigo) {
		if (codigo == null) {
			return null;

		}

		for (TipoPostagem tipo : TipoPostagem.values()) {
			if (codigo == tipo.getCodigo()) {
				return tipo;
			}
		}

		throw new IllegalArgumentException("Código inválido: " + codigo);
	}

}
