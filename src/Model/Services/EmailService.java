package Model.Services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
	public static void sendEmail(String subject, String body) throws Exception {
		String from = "guillermogalavisg@gmail.com";
		String password = "khkk lycv luxr mqbx";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(from));
		message.setSubject(subject);
		message.setText(body);

		Transport.send(message);
	}
}
