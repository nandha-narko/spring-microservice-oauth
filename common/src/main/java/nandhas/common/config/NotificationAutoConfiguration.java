package nandhas.common.config;

import nandhas.common.notification.NotificationSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * NotificationAutoConfiguration
 */
@Configuration
@ConditionalOnProperty(name = "nandhas.enableNotification", havingValue = "true")
@ComponentScan("nandhas.common.notification")
@PropertySource("classpath:application.properties")
@EnableBinding(NotificationSource.class)
public class NotificationAutoConfiguration {

}