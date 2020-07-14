package nandhas.authservice.event.listener;

import java.util.HashMap;

import nandhas.authservice.event.SendActivationEvent;
import nandhas.common.client.UserServiceClient;
import nandhas.common.dto.userservice.TokenDto;
import nandhas.common.dto.userservice.UserDto;
import nandhas.common.enums.TokenType;
import nandhas.common.notification.Notification;
import nandhas.common.notification.NotificationTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * RegistrationCompleteListener
 */
@Component
public class SendActivationListener implements ApplicationListener<SendActivationEvent> {

    @Autowired
    UserServiceClient userServiceClient;

    @Override
    public void onApplicationEvent(SendActivationEvent event) {
        final UserDto userDto = event.getUser();
        final TokenDto tokenDto = userServiceClient.createToken(new TokenDto(TokenType.Activation, userDto.getId()));
        sendNotification(userDto, tokenDto, event);
    }

    private void sendNotification(UserDto userDto, TokenDto tokenDto, SendActivationEvent event) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("url", event.getAppUrl() + "/activate/" + tokenDto.getToken());
        params.put("name", userDto.getName());

        Notification notification = new Notification.Builder(NotificationTemplate.Registration).to(userDto.getEmail())
                .params(params).build();
        notification.Send();
    }
}