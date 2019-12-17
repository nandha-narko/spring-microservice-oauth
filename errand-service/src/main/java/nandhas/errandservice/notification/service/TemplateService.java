package nandhas.errandservice.notification.service;

import nandhas.common.notification.Notification;

import org.springframework.stereotype.Service;

/**
 * NotificationService
 */
@Service
public interface TemplateService {

    public Notification processTemplate(Notification notification);

}