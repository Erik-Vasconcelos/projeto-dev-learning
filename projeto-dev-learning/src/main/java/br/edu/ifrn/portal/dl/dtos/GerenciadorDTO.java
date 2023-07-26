package br.edu.ifrn.portal.dl.dtos;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.ifrn.portal.dl.models.Gerenciador;
import br.edu.ifrn.portal.dl.models.enuns.TipoGerencidor;
import br.edu.ifrn.portal.dl.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe reponsável por montar o objeto de tranferência de dados para o form de
 * um <strong>Gerenciador<strong>. O padrão de DTO vai criar uma camada de
 * proteção para não haver inserções maliciosas em formulários.
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-24
 * @version 0.1
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class GerenciadorDTO implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@NonNull
	private Long id;

	@NotNull(message = "O tipo do gerenciador é obrigatório!")
	private TipoGerencidor tipoGerenciador;

	@NonNull
	@NotBlank(message = "O nome é obrigatório!")
	@Size(min = 3, max = 40, message = "O tamanho do título deve ser entre {min} e {max} caracteres")
	private String nome;

	@NonNull
	@Email(regexp = "^(?=.{1,64}@)[\\p{L}0-9\\+_-]+(\\.[\\p{L}0-9\\+_-]+)*@"
			+ "[^-][\\p{L}0-9\\+-]+(\\.[\\p{L}0-9\\+-]+)*(\\.[\\p{L}]{2,})$", message = "Email inválido")
	private String email;

	@NonNull
	@NotBlank(message = "A senha é obrigatória!")
	private String senha;

	@NonNull
	private String imagem;

	@ManyToMany
	@Size(min = 1, message = "Insira ao menos uma role para o gerenciador!")
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

	public Gerenciador toGerenciador() {
		Gerenciador gerenciador = new Gerenciador();
		gerenciador.setId(this.id);
		gerenciador.setNome(this.nome);
		gerenciador.setTipoGerenciador(this.tipoGerenciador);

		gerenciador.setEmail(this.email);
		gerenciador.setSenha(this.senha);
		gerenciador.setImagem(this.imagem);
		gerenciador.setRoles(this.roles);

		return gerenciador;
	}

	public Gerenciador configAttibutes(Gerenciador gerenciador) {
		gerenciador.setId(this.id);
		gerenciador.setNome(this.nome);
		gerenciador.setTipoGerenciador(this.tipoGerenciador);

		gerenciador.setEmail(this.email);
		gerenciador.setSenha(this.senha);
		gerenciador.setImagem(this.imagem);
		gerenciador.setRoles(this.roles);

		if (!imagem.isBlank()) {
			gerenciador.setImagem(this.imagem);
		} else {
			gerenciador.setImagem("");
		}

		return gerenciador;
	}

	public void fromGerenciadorDTO(Gerenciador gerenciador) {
		this.id = gerenciador.getId();
		this.nome = gerenciador.getNome();
		this.tipoGerenciador = gerenciador.getTipoGerenciador();
		this.email = gerenciador.getEmail();
		this.email = gerenciador.getEmail();
		this.senha = gerenciador.getSenha();
		this.roles = gerenciador.getRoles();
		this.imagem = gerenciador.getImagem();
	}

	public boolean isEmpty() {
		try {
			boolean containsNome = nome == null ? false : !nome.isBlank();
			boolean containsTipo = tipoGerenciador == null ? false : true;
			boolean containsEmail = email == null ? false : !email.isBlank();
			boolean containsSenha = senha == null ? false : !senha.isBlank();
			boolean containsImagem = imagem == null ? false : !imagem.isBlank();
			boolean containsRoles = roles == null ? false : !roles.isEmpty();

			if (containsNome || containsTipo || containsEmail || containsSenha || containsImagem || containsRoles) {
				return false;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return true;
	}

}
