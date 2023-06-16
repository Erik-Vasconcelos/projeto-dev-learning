package br.edu.ifrn.portal.dl.dtos;

import java.io.File;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import br.edu.ifrn.portal.dl.models.Disciplina;
import br.edu.ifrn.portal.dl.utils.ConversorImagem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe reponsável por montar o objeto de tranferência de dados para uma nova
 * <strong>Disciplina<strong>. O padrão de DTO vai garantir que não haverá
 * inserções maliciosas em formulários.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-15
 * @version A0.1
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class DisciplinaFormDTO {

	@NonNull
	@NotBlank(message = "O nome é obrigatório!")
	@Size(min = 3, max = 100, message = "O tamanho do nome deve ser entre {min} e {max} caracteres")
	private String nome;

	@NonNull
	@NotBlank(message = "A descrição dos objetivos da disciplina é obrigatória!")
	@Size(min = 30, max = 1000, message = "A descrição dos objetivo deve ter no minímo {min} e no máximo {max} caracteres")
	private String descricaoObjetivos;

	@NonNull
	@NotNull(message = "A Imagem da disciplina é obrigatória!")
	private MultipartFile imagem;
	
	public Disciplina toDisciplina() {
		return new Disciplina(this.nome, this.descricaoObjetivos, ConversorImagem.getImagemEncoded(this.imagem));
	}
	
	public void fromDisciplinaDTO(Disciplina disciplina) {
		this.nome = disciplina.getNome();
		this.descricaoObjetivos = disciplina.getDescricaoObjetivos();
//		this.imagem = disciplina.getImagem();
	}

	/*messages.properties define as mensagem de erros das validacoes
	 * definicao:NomeDaValidacao.classeDeValidacao.atributo
	 * NotNull.requisicaoNovoProfessor.nome = O nome do professor nao pode ser nullo!
	 * */
}
