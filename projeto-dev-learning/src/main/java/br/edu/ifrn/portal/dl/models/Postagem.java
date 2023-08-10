package br.edu.ifrn.portal.dl.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
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

@NamedNativeQueries(value = {
	@NamedNativeQuery(name = "Postagem.countPostsByType", query = "SELECT p.tipo_postagem, COUNT(p.id) AS quantidade FROM postagens p"
			+ " INNER JOIN disciplinas d ON d.id = p.disciplina_id"
			+ " GROUP BY p.tipo_postagem", resultSetMapping = "countPostsByTypeMapping"),
	
	@NamedNativeQuery(name = "Postagem.countPostByDisciplinas", query = "SELECT d.nome AS disciplina, COUNT(p.id) AS quantidade FROM postagens p"
			+ " INNER JOIN disciplinas d On d.id = p.disciplina_id"
			+ " GROUP BY disciplina ORDER BY disciplina ASC" , resultSetMapping = "countPostByDisciplinasMapping")
})

@SqlResultSetMappings(value = {
	@SqlResultSetMapping(name = "countPostsByTypeMapping", classes = {
			@ConstructorResult(targetClass = br.edu.ifrn.portal.dl.utils.InfoPostagens.class, columns = {
					@ColumnResult(name = "tipo_postagem", type = Integer.class),
					@ColumnResult(name = "quantidade", type = Long.class) 
			}) 
	}),
	
	@SqlResultSetMapping(name = "countPostByDisciplinasMapping", classes = {
			@ConstructorResult(targetClass = br.edu.ifrn.portal.dl.utils.InfoDisciplina.class, columns = {
					@ColumnResult(name = "disciplina", type = String.class),
					@ColumnResult(name = "quantidade", type = Long.class) 
			}) 
	})
})
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
	@Column(nullable = false, columnDefinition = "TEXT", length = 4194304) /* TODO */
	private String corpo;

	@Column(nullable = false, columnDefinition = "TEXT", length = 4194304)
	private String html;

	@ManyToOne(optional = false)
	private Disciplina disciplina;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Tecnologia> tecnologias = new LinkedHashSet<>();
	
	@ManyToOne(optional = false)
	private Gerenciador autor; 
	
	private LocalDate dataPostagem;

	public Postagem(TipoPostagem tipo, String titulo, String imagem, String corpo, String html, String trechoHtml,
			Disciplina disciplina, LocalDate dataPostagem) {
		setTipoPostagem(tipo);
		this.titulo = titulo;
		this.imagem = imagem;
		this.corpo = corpo;
		this.html = html;
		this.disciplina = disciplina;
		this.dataPostagem = dataPostagem;
	}

	public TipoPostagem getTipoPostagem() {
		return TipoPostagem.toEnum(tipoPostagem);
	}

	public void setTipoPostagem(TipoPostagem tipo) {
		this.tipoPostagem = tipo.getCodigo();
	}

}
