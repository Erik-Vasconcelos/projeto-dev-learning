package br.edu.ifrn.portal.dl.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)	
	private String nomeRole;
	
	@Override
	public String getAuthority() {
		return this.nomeRole;
	}

	/*Para inseir no banco de dados você precisa seguir o seguinte formato: ROLE_<NOME-ROLE>.
	 *  Para usar dentr do spring você só precisa colocar o nome da role*/
	
}
