package br.edu.ifrn.portal.dl.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.edu.ifrn.portal.dl.models.Tecnologia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe reponsável por montar o objeto de tranferência de dados para o form de
 * uma <strong>Tecnologia<strong>. O padrão de DTO vai garantir que não haverá
 * inserções maliciosas em formulários.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-22
 * @version A0.1
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class TecnologiaFormDTO {

	@NonNull
	@NotBlank(message = "O nome é obrigatório!")
	@Size(min = 3, max = 30, message = "O tamanho do nome deve ser entre {min} e {max} caracteres")
	private String nome;

	public Tecnologia toTecnologia() {
		Tecnologia tecnologia = new Tecnologia(this.nome);
		return tecnologia;
	}

	public Tecnologia configAttibutes(Tecnologia tecnologia) {
		tecnologia.setNome(this.nome);
		return tecnologia;
	}

	public void fromTecnologiaDTO(Tecnologia tecnologia) {
		this.nome = tecnologia.getNome();
	}

	public boolean isEmpty() {
		try {
			boolean containsNome = nome == null ? false : !nome.isBlank();
			if (containsNome) {
				return false;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return true;
	}

}
