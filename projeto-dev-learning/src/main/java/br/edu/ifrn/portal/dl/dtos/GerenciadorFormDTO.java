package br.edu.ifrn.portal.dl.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifrn.portal.dl.models.Gerenciador;
import br.edu.ifrn.portal.dl.models.enuns.TipoGerencidor;
import br.edu.ifrn.portal.dl.utils.ConversorImagem;
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
 * @since 2023-08-20
 * @version 0.1
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class GerenciadorFormDTO {

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
	@Size(min = 8, message ="A senha deve ter no mínimo {min} caracteres!")
	private String senha;

	@NonNull
	private String imagem;

	private MultipartFile imagemFile;

	public Gerenciador toGerenciador() {
		Gerenciador gerenciador = new Gerenciador();
		gerenciador.setId(this.id);
		gerenciador.setNome(this.nome);
		gerenciador.setTipoGerenciador(this.tipoGerenciador);

		gerenciador.setEmail(this.email);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		gerenciador.setSenha(encoder.encode(this.senha));

		if (imagemFile.getSize() > 0) {
			gerenciador.setImagem(ConversorImagem.getImagemEncoded(imagemFile));
		}

		return gerenciador;
	}

	public Gerenciador configAttibutes(Gerenciador gerenciador) {
		gerenciador.setId(this.id);
		gerenciador.setNome(this.nome);
		gerenciador.setTipoGerenciador(this.tipoGerenciador);

		gerenciador.setEmail(this.email);
		gerenciador.setImagem(this.imagem);

		if (imagemFile.getSize() > 0) {
			gerenciador.setImagem(ConversorImagem.getImagemEncoded(imagemFile));
		}

		return gerenciador;
	}

	public void fromGerenciadorDTO(Gerenciador gerenciador) {
		this.id = gerenciador.getId();
		this.nome = gerenciador.getNome();
		this.tipoGerenciador = gerenciador.getTipoGerenciador();
		this.email = gerenciador.getEmail();
		this.senha = gerenciador.getSenha();
		this.imagem = gerenciador.getImagem();
	}

	public boolean isEmpty() {
		try {
			boolean containsNome = nome == null ? false : !nome.isBlank();
			boolean containsTipo = tipoGerenciador == null ? false : true;
			boolean containsEmail = email == null ? false : !email.isBlank();
			boolean containsSenha = senha == null ? false : !senha.isBlank();
			boolean containsImagem = imagem == null ? false : !imagem.isBlank();

			if (containsNome || containsTipo || containsEmail || containsSenha || containsImagem) {
				return false;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return true;
	}

}
