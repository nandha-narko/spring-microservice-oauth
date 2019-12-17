package nandhas.errandservice.notification;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * NotificationSource
 */
public interface NotificationProcessor {

    String INPUT = "notificationInput";

    /**
     * @return input channel.
     */
    @Input(NotificationProcessor.INPUT)
    SubscribableChannel input();

    String OUTPUT = "notificationOutput";

    /**
     * @return output channel.
     */
    @Output(NotificationProcessor.OUTPUT)
    MessageChannel output();
}