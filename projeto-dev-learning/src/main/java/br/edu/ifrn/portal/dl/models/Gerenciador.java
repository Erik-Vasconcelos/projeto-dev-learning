package br.edu.ifrn.portal.dl.models;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.edu.ifrn.portal.dl.models.enuns.TipoGerencidor;
import br.edu.ifrn.portal.dl.security.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe modelo da <strong>entidade Gerenciador<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-21
 * @version A0.1
 */

@NoArgsConstructor
@Data
@Entity
@Table(name = "gerenciadores")
public class Gerenciador implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer tipoGerenciador;

	@Column(nullable = false, unique = true)
	private String nome;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false, columnDefinition = "TEXT", length = 4194304)
	private String imagem;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new LinkedHashSet<>();

	public Gerenciador(TipoGerencidor tipoGerencidor, String nome, String email, String senha, String imagem) {
		if(tipoGerencidor != null) {
			this.tipoGerenciador = tipoGerencidor.getCodigo();
		}
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.imagem = imagem;
	}

	public TipoGerencidor getTipoGerenciador() {
		return TipoGerencidor.toEnum(tipoGerenciador);
	}

	public void setTipoGerenciador(TipoGerencidor tipo) {
		this.tipoGerenciador = tipo.getCodigo();
	}

}
