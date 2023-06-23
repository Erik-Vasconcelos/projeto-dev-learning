package br.edu.ifrn.portal.dl.dtos;

import javax.validation.constraints.NotBlank;
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
 * Classe reponsável por montar o objeto de tranferência de dados para o form de uma 
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

	private MultipartFile imagemFile;
	
	@NonNull
	private String imagem;
	
	public Disciplina toDisciplina() {
		Disciplina disciplina = new Disciplina();
		disciplina.setNome(this.nome);
		disciplina.setDescricaoObjetivos(this.descricaoObjetivos);
		
		if(imagemFile.getSize() > 0) {
			disciplina.setImagem(ConversorImagem.getImagemEncoded(imagemFile));
		}
		
		return disciplina;
	}
	
	public Disciplina configAttibutes(Disciplina disciplina) {
		disciplina.setNome(this.nome);
		disciplina.setDescricaoObjetivos(this.descricaoObjetivos);
		
		if(imagemFile.getSize() > 0) {
			disciplina.setImagem(ConversorImagem.getImagemEncoded(imagemFile));
		}
		
		return disciplina;
	}
	
	public void fromDisciplinaDTO(Disciplina disciplina) {
		this.nome = disciplina.getNome();
		this.descricaoObjetivos = disciplina.getDescricaoObjetivos();
		this.imagem = disciplina.getImagem();
	}
	
	public boolean isEmpty() {
		try {
			boolean containsNome = nome == null ? false : !nome.isBlank();
			boolean containsDescricao = descricaoObjetivos == null ? false : !descricaoObjetivos.isBlank();
			boolean containsImage = imagemFile == null ? false : !imagemFile.isEmpty();
			
			if(containsNome || containsDescricao || containsImage) {
				return false;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/*messages.properties define as mensagem de erros das validacoes
	 * definicao:NomeDaValidacao.classeDeValidacao.atributo
	 * NotNull.requisicaoNovoProfessor.nome = O nome do professor nao pode ser nullo!
	 * */
}
