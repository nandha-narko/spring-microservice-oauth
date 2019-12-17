package nandhas.common.notification;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * NotificationSource
 */
public interface NotificationSource {

    String OUTPUT = "notifications";

    /**
     * @return output channel.
     */
    @Output(NotificationSource.OUTPUT)
    MessageChannel output();
}