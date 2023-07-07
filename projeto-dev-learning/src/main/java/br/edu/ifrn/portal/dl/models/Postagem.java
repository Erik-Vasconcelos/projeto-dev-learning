package br.edu.ifrn.portal.dl.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.ifrn.portal.dl.models.enuns.TipoPostagem;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Classe modelo da <strong>entidade Postagem<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-28
 * @version A0.1
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "postagens")
public class Postagem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer tipoPostagem;
	
	@NonNull
	@Column(nullable = false, unique = true)
	private String titulo;
	
	@NonNull
	@Column(nullable = false, columnDefinition = "TEXT", length = 4194304)
	private String imagem;
	
	@NonNull
	@Column(nullable = false, columnDefinition = "TEXT", length = 4194304) /*TODO*/
	private String corpo;
	
	private String html;
	
	private String trechoHtml;
	
	@ManyToOne(optional = false)
	private Disciplina disciplina;
	
	@ManyToMany
	private List<Tecnologia> tecnologias = new LinkedList<>();

	public Postagem(TipoPostagem tipo, String titulo, String imagem, String corpo, String html, String trechoHtml,
			Disciplina disciplina) {
		setTipoPostagem(tipo);
		this.titulo = titulo;
		this.imagem = imagem;
		this.corpo = corpo;
		this.html = html;
		this.trechoHtml = trechoHtml;
		this.disciplina = disciplina;
	}
	
	public TipoPostagem getTipoPostagem() {
		return TipoPostagem.toEnum(tipoPostagem);
	}
	
	public void setTipoPostagem(TipoPostagem tipo) {
		this.tipoPostagem = tipo.getCodigo();
	}

}
