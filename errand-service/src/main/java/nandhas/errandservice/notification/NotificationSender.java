package nandhas.errandservice.notification;

import javax.mail.MessagingException;

import nandhas.common.notification.Notification;
import nandhas.common.utils.MapperUtil;
import nandhas.errandservice.notification.model.EmailNotification;
import nandhas.errandservice.notification.service.EmailService;
import nandhas.errandservice.notification.service.TemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.Transformer;

/**
 * NotificationListener
 */
@EnableBinding(NotificationProcessor.class)
public class NotificationSender {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private EmailService emailService;

    @Transformer(inputChannel = NotificationProcessor.INPUT, outputChannel = NotificationProcessor.OUTPUT)
    public Notification processNotification(Notification notification) {
        System.out.println(notification);
        return templateService.processTemplate(notification);
    }

    @StreamListener(target = NotificationProcessor.OUTPUT)
    public void sendNotification(Notification notification) throws MessagingException {
        emailService.sendEmail(MapperUtil.Convert(notification, EmailNotification.class));
    }
}