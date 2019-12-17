package nandhas.errandservice.notification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import javax.mail.MessagingException;

import nandhas.common.notification.Notification;
import nandhas.common.notification.NotificationTemplate;
import nandhas.errandservice.notification.service.TemplateService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * NotificationProcessorTests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
public class NotificationSenderTests {

    @SpyBean
    private NotificationSender notificationSender;

    @MockBean
    private TemplateService templateService;

    @Autowired
    private NotificationProcessor notificationProcessor;

    @Before
    public void prepareMocks() throws MessagingException {
        Notification notification = getTestData();
        when(templateService.processTemplate(any(Notification.class))).thenReturn(notification);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProcessNotification() throws InterruptedException {
        Notification sentNotification = getTestData();
        notificationProcessor.input().send(MessageBuilder.withPayload(sentNotification).build());
        verify(this.notificationSender, times(1)).processNotification(any(Notification.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSendNotification() throws InterruptedException, MessagingException {
        Notification sentNotification = getTestData();
        notificationProcessor.input().send(MessageBuilder.withPayload(sentNotification).build());
        verify(this.notificationSender, times(1)).sendNotification(any(Notification.class));
    }

    private Notification getTestData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("url", "http://localhost:8080/activate/");
        params.put("name", "Nandha");
        return new Notification.Builder(NotificationTemplate.Registration).to("nantha.sakthivel@tavant.com").build();
    }

}