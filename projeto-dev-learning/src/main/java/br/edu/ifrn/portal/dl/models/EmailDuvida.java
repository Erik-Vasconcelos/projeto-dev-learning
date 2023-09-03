package br.edu.ifrn.portal.dl.models;

import br.edu.ifrn.portal.dl.models.enuns.StatusEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe modelo para o contato dos usuário com os gerenciadores
 * 
 * @author Erik Vasconcelos
 * @since 2023-09-02
 * @version 1.0 2023-09-03
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDuvida {

	private String nomeRemetente;
	private String emailRemetente;
	private String tituloPostagem;
	private String disciplinaPostagem;
	private String assunto;
	private String corpo;
	private StatusEmail status;

	public String getCorpoFormatado() {
		StringBuilder emailFormatado = new StringBuilder();
		emailFormatado.append("<h2>Olá, uma nova dúvida surgiu no blog!</h2><br>");
		emailFormatado.append("<h3>Título da postagem: ").append(this.tituloPostagem + "</h3>");
		emailFormatado.append("<h4>Disciplina da postagem: ").append(this.disciplinaPostagem + "</h4><br>");
		emailFormatado.append("Nome do remetente: ").append(this.nomeRemetente).append("<br>");
		emailFormatado.append("Email do remetente: ").append("<a href='mailto:").append(this.emailRemetente)
				.append("' target='_blank'>").append(this.emailRemetente).append("</a><br><br>");
		emailFormatado.append("<h3><b>Dúvida:</b></h3>");
		emailFormatado.append("<p>").append(this.corpo).append("</p>");

		return emailFormatado.toString();
	}

}
