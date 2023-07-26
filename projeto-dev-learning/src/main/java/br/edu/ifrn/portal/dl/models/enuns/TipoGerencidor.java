package br.edu.ifrn.portal.dl.models.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum do tipo da postagem
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-28
 * @version A0.1
 */

@AllArgsConstructor
@Getter
public enum TipoGerencidor {

	ESCRITOR(1, "Escritor"),
	ADMIN(2, "Administrador"),
	ADMIN_MASTER(3, "Administrador Master");

	private int codigo;
	private String descricao;

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static TipoGerencidor toEnum(Integer codigo) {
		if (codigo == null) {
			return null;

		}

		for (TipoGerencidor tipo : TipoGerencidor.values()) {
			if (codigo == tipo.getCodigo()) {
				return tipo;
			}
		}

		throw new IllegalArgumentException("Código inválido: " + codigo);
	}

}
