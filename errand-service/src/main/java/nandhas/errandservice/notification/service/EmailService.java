package nandhas.errandservice.notification.service;

import javax.mail.MessagingException;

import nandhas.errandservice.notification.model.EmailNotification;;

/**
 * EmailService
 */
public interface EmailService {

    void sendEmail(EmailNotification notification) throws MessagingException ;
    
}