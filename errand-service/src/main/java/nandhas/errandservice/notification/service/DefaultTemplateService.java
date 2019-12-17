package nandhas.errandservice.notification.service;

import java.util.Set;

import nandhas.common.notification.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * DefaultNotificationService
 */
@Service
public class DefaultTemplateService implements TemplateService {

    @Autowired
    TemplateEngine htmlTemplateEngine;

    @Autowired
    EmailService emailService;

    @Override
    public Notification processTemplate(Notification notification) {
        Context context = getContext(notification);
        String htmlContent = this.htmlTemplateEngine.process(notification.getNotificationTemplate().name().toLowerCase(), context);
        notification.setBody(htmlContent);
        return notification;
    }

    private Context getContext(Notification notification) {
        Context context = new Context();
        Set<String> keySet = notification.getParams().keySet();
        keySet.forEach(key -> {
            context.setVariable(key, notification.getParams().get(key));
        });
        return context;
    }
}