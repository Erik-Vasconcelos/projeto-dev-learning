package br.edu.ifrn.portal.dl.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author erikv
 * @since 13/06/2023
 * 
 */

@Entity
@Table(name = "disciplinas")
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 3, max = 100, message = "O tamanho do nome deve ser entre {min} e {max} caracteres")
	@NotBlank(message = "O nome é obrigatório!")
	private String nome;
	
	@NotBlank(message = "O resumo da disciplina é obrigatório!")
	@Column(length = 1000)
	private String resumo;
	
	//@NotNull(message = "A imagem da disciplina é obrigatória!")
	@Column(columnDefinition = "TEXT", length = 4194304)
	private String imagem;

	public Disciplina() {
	}

	public Disciplina(
			@Size(min = 3, max = 100, message = "O tamanho do nome deve ser entre {min} e {max} caracteres") @NotBlank(message = "O nome é obrigatório!") String nome,
			@NotBlank(message = "O resumo da disciplina é obrigatório!") String resumo,
			@NotNull(message = "A imagem da disciplina é obrigatória!") String imagem) {
		this.nome = nome;
		this.resumo = resumo;
		this.imagem = imagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disciplina other = (Disciplina) obj;
		return Objects.equals(id, other.id);
	}
	
}
