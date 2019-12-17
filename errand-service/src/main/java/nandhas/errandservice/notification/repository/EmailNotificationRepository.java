package nandhas.errandservice.notification.repository;

import nandhas.errandservice.notification.model.EmailNotification;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * NotificationRepository
 */
public interface EmailNotificationRepository extends ReactiveCrudRepository<EmailNotification, String> {
       
}