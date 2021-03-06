package bg.unisofia.fmi.docmag.email;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;

@Component
public class EmailSender {
	
	private final String USERNAME="docmagsystem@gmail.com";
	private final String PASSWORD="docmag1201";

	private Properties properties;

	@PostConstruct
	private void init(){
		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
	}

	public void sendEmail(String to, String subject, String text){
		Session session = Session.getInstance(this.properties,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("docmagsystem@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(text,"text/html");
			Transport.send(message);

		} catch (MessagingException e) {
			System.out.println("Sending email failed.Message:" + e.getMessage());
		}
	}
	
	public String generateTextForThesisProposal(ThesisProposalStatus status, String name){
		String text = "<p>Dear " + name + ", </p>"+
				"<p>We are glad to announce that your thesis proposal has been approved </p>"+
				"<p>Wishing you good luck on the upcomming defence.</p>"+
				"<p>SU Team</p>";
		return text;
	}
}
