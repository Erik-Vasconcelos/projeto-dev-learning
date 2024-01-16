package br.edu.ifrn.portal.dl.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


/**
 * Classe modelo para a Role usada no spring security
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-20
 * @version 1.0 2023-09-03
 */


@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Column(nullable = false, unique = true)
	private String nomeRole;
	
	@NonNull
	@Column(unique = true)
	private int nivelPermissao;

	@Override
	public String getAuthority() {
		return this.nomeRole;
	}

}
