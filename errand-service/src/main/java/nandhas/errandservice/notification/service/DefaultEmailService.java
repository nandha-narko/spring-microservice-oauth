package nandhas.errandservice.notification.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import nandhas.errandservice.notification.model.EmailNotification;
import nandhas.errandservice.notification.repository.EmailNotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * DefaultEmailService
 */
@Service
public class DefaultEmailService implements EmailService {

    private final String from = "noreply.nandha@gmail.com";

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    EmailNotificationRepository repository;

    @Override
    public void sendEmail(EmailNotification emailNotification) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = getMessage(mimeMessage, emailNotification);
            message.setText(emailNotification.getBody(), true);

            // this.mailSender.send(mimeMessage);
            emailNotification.setSent(true);
        } catch (MessagingException ex) {

        } catch (MailException ex) {

        } catch (Exception ex) {

        }

        saveEmailNotification(emailNotification);
    }

    private MimeMessageHelper getMessage(MimeMessage mimeMessage, EmailNotification emailNotification)
            throws MessagingException {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setFrom(emailNotification.getFrom() != null ? emailNotification.getFrom() : from);
        message.setTo(emailNotification.getTo());
        message.setSubject(emailNotification.getSubject());
        return message;
    }

    private void saveEmailNotification(EmailNotification emailNotification) {
        this.repository.save(emailNotification).subscribe(result -> System.out.println(result));
    }

}