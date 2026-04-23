package se.iths.erikthorell.springmessenger.messaging;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;
import se.iths.erikthorell.springmessenger.model.Email;
import se.iths.erikthorell.springmessenger.model.Message;

import java.util.Properties;

@Component("email")
public class EmailSender implements Messenger {
    private final String FROM = System.getenv("MAIL_ACCOUNT");
    private final String APP_PASSWORD = System.getenv("MAIL_PASSWORD");


    @Override
    public void send(Message message) {
        if (!(message instanceof Email email)) {
            throw new IllegalArgumentException("Fel typ av meddelande");
        }
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, APP_PASSWORD);
            }
        });
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(FROM));
            mimeMessage.setRecipient(jakarta.mail.Message.RecipientType.TO,
                    new InternetAddress(email.getRecipient()));

            mimeMessage.setSubject(email.getSubject());
            mimeMessage.setText(email.getMessage());
            Transport.send(mimeMessage);

            System.out.println("Mail sent");
        } catch (Exception e) {
            System.out.println("Error sending message: " + e.getMessage());
        }

    }
}
