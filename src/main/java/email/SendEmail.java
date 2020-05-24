package email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Responsible for send message to email
 */
public class SendEmail {

    protected static Properties properties = new Properties();
    protected final static String ACC_EMAIL = "SchedulerToOptimizeYourTime@gmail.com";
    protected final static String ACC_PASSWORD = "Y32Xz81pt";

    private static SendEmail instance = new SendEmail();

    public static SendEmail getInstance() {
        return instance;
    }

    private SendEmail() {
        loadProperties();
    }

    /**
     * Loading properties to prepare for sending a message
     */
    private void loadProperties() {
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    }

    /**
     * Send
     *
     * @param recipient Who will receive the message
     * @param subject Title of message
     * @param text Just body of message
     * @return
     */
    public boolean send(String recipient, String subject, String text) {
        try {
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(ACC_EMAIL, ACC_PASSWORD);
                }
            };
            Session session = Session.getInstance(properties, auth);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ACC_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSentDate(new Date());
            message.setSubject(subject);
            message.setContent(text, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
