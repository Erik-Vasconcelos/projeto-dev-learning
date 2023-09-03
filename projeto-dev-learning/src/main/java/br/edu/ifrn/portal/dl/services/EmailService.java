package br.edu.ifrn.portal.dl.services;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.models.EmailDuvida;
import br.edu.ifrn.portal.dl.models.enuns.StatusEmail;

/**
 * Classe responsável por disponibilizar os servicos de <strong>E-mails<strong>
 * 
 * @author Erik Vasconcelos
 * @since 2023-09-03
 * @version 1.0 2023-09-03
 */

@Service
public class EmailService {

	private static final String EMAIL_BLOG = "devlearningblog@gmail.com";
	
	@Autowired
	private JavaMailSender emailSender;
	
	public void sendEmail(EmailDuvida email) {
		try {
			MimeMessagePreparator mensagem = new MimeMessagePreparator() {
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setFrom(new InternetAddress(EMAIL_BLOG, email.getNomeRemetente()));
					mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(EMAIL_BLOG));
					mimeMessage.setSubject(email.getAssunto());
					mimeMessage.setContent(email.getCorpoFormatado(), "text/html; charset=utf-8");
				}
			};
			
			emailSender.send(mensagem);
			
			email.setStatus(StatusEmail.ENVIADO);
		} catch (MailException e) {
			e.printStackTrace();
			email.setStatus(StatusEmail.ERROR);
		}
	}
	
}
