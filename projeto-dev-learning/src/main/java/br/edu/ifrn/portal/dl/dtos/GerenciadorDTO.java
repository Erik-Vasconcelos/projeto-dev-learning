package br.edu.ifrn.portal.dl.dtos;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifrn.portal.dl.models.Gerenciador;
import br.edu.ifrn.portal.dl.models.enuns.TipoGerencidor;
import br.edu.ifrn.portal.dl.security.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe reponsável por montar o objeto de tranferência de dados para o login de
 * um <strong>Gerenciador<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-24
 * @version 0.2
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GerenciadorDTO implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private TipoGerencidor tipoGerenciador;

	private String nome;

	private String email;

	private String senha;

	private String imagem;

	private Set<Role> roles = new LinkedHashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

//	public Gerenciador toGerenciador() {
//		Gerenciador gerenciador = new Gerenciador();
//		gerenciador.setId(this.id);
//		gerenciador.setNome(this.nome);
//		gerenciador.setTipoGerenciador(this.tipoGerenciador);
//
//		gerenciador.setEmail(this.email);
//		
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		gerenciador.setSenha(encoder.encode(this.senha));
//
//		if (imagemFile.getSize() > 0) {
//			gerenciador.setImagem(ConversorImagem.getImagemEncoded(imagemFile));
//		}
//		gerenciador.setRoles(this.roles);
//
//		return gerenciador;
//	}
//
//	public Gerenciador configAttibutes(Gerenciador gerenciador) {
//		gerenciador.setId(this.id);
//		gerenciador.setNome(this.nome);
//		gerenciador.setTipoGerenciador(this.tipoGerenciador);
//
//		gerenciador.setEmail(this.email);
//		gerenciador.setImagem(this.imagem);
//		gerenciador.setRoles(this.roles);
//
//		if (imagemFile.getSize() > 0) {
//			gerenciador.setImagem(ConversorImagem.getImagemEncoded(imagemFile));
//		}
//
//		return gerenciador;
//	}

	public void fromGerenciadorDTO(Gerenciador gerenciador) {
		this.id = gerenciador.getId();
		this.nome = gerenciador.getNome();
		this.tipoGerenciador = gerenciador.getTipoGerenciador();
		this.email = gerenciador.getEmail();
		this.roles = gerenciador.getRoles();
		this.senha = gerenciador.getSenha();
		this.imagem = gerenciador.getImagem();
	}

//	public boolean isEmpty() {
//		try {
//			boolean containsNome = nome == null ? false : !nome.isBlank();
//			boolean containsTipo = tipoGerenciador == null ? false : true;
//			boolean containsEmail = email == null ? false : !email.isBlank();
//			boolean containsSenha = senha == null ? false : !senha.isBlank();
//			boolean containsImagem = imagem == null ? false : !imagem.isBlank();
//			boolean containsRoles = roles == null ? false : !roles.isEmpty();
//
//			if (containsNome || containsTipo || containsEmail || containsSenha || containsImagem || containsRoles) {
//				return false;
//			}
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		}
//
//		return true;
//	}

}
