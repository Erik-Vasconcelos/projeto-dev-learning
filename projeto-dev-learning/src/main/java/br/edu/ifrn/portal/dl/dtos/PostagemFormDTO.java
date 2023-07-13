package br.edu.ifrn.portal.dl.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import br.edu.ifrn.portal.dl.models.Disciplina;
import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.models.enuns.TipoPostagem;
import br.edu.ifrn.portal.dl.utils.ConversorImagem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe reponsável por montar o objeto de tranferência de dados para o form de uma 
 * <strong>Postagem<strong>. O padrão de DTO vai criar uma camada de proteção para não haver
 * inserções maliciosas em formulários.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-29
 * @version A0.1
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class PostagemFormDTO {

	@NonNull
	@NotBlank(message = "O título é obrigatório!")
	@Size(min = 3, max = 150, message = "O tamanho do título deve ser entre {min} e {max} caracteres")
	private String titulo;
	
	@NotNull(message = "O tipo da postagem é obrigatório!")
	private TipoPostagem tipoPostagem;
	
	@NonNull
	private String imagemBanner;
	
	private MultipartFile imagemFile;

	@NotNull(message = "A disciplina é obrigatória!")
	private Disciplina disciplina;
	
	@NotBlank(message = "Insira ao menos uma tecnologia para ser relacionada a postagem")
	private String tecnologiaTemp;
	
	@NotNull(message = "A(s) tecnologia(s) relacionada(s) a postagem é/são obrigatória(s)!")
	private List<Tecnologia> tecnologias = new ArrayList<>();
	
	@NonNull
	@NotBlank(message = "O corpo da postagem é obrigatório!")
	@Size(min = 30, max = 20000, message = "O corpo da postagem deve ter no minímo {min} e no máximo {max} caracteres")
	private String corpo;
	
	@NonNull
	private String html;
	
	/*@NonNull
	private String trechoHtml;*/
	
	public Postagem toPostagem() {
		Postagem postagem = new Postagem();
		postagem.setTitulo(this.titulo);
		postagem.setTipoPostagem(this.tipoPostagem);
		
		if(imagemFile.getSize() > 0) {
			postagem.setImagem(ConversorImagem.getImagemEncoded(imagemFile));
		}
		
		postagem.setDisciplina(disciplina);
		postagem.setTecnologias(tecnologias);
		postagem.setCorpo(corpo);
		postagem.setHtml(html);
		//postagem.setTrechoHtml(trechoHtml);
		
		return postagem;
	}
	
	public Postagem configAttibutes(Postagem postagem) {
		postagem.setTitulo(this.titulo);
		postagem.setTipoPostagem(this.tipoPostagem);
		
		if(imagemFile.getSize() > 0) {
			postagem.setImagem(ConversorImagem.getImagemEncoded(imagemFile));
		}
		
		postagem.setDisciplina(disciplina);
		postagem.setTecnologias(tecnologias);
		postagem.setCorpo(corpo);
		postagem.setHtml(html);
		//postagem.setTrechoHtml(trechoHtml);
		
		return postagem;
	}
	
	public void fromPostagemDTO(Postagem postagem) {
		this.titulo = postagem.getTitulo();
		this.tipoPostagem = postagem.getTipoPostagem();
		this.imagemBanner = postagem.getImagem();
		
		this.disciplina = postagem.getDisciplina();
		this.tecnologias = postagem.getTecnologias();
		
		this.corpo = postagem.getCorpo();
		this.html = postagem.getHtml();
		//this.trechoHtml = postagem.getTrechoHtml();
	}
	
	public boolean isEmpty() {
		try {
			boolean containsTitulo = titulo == null ? false : !titulo.isBlank();
			boolean containsTipo = tipoPostagem == null ? false : true;
			boolean containsImage = imagemFile == null ? false : !imagemFile.isEmpty();
			boolean containsDisciplina = disciplina == null ? false : true;
			boolean containsTecnologias = tecnologiaTemp == null || tecnologiaTemp.isBlank() ? false : true;
			boolean containsCorpo = corpo == null ? false : !corpo.isBlank();
			
			if(containsTitulo || containsTipo || containsImage || containsDisciplina || containsTecnologias || containsCorpo) {
				return false;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
