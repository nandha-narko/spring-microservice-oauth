package nandhas.authservice.event;

import nandhas.common.dto.userservice.UserDto;

import org.springframework.context.ApplicationEvent;

/**
 * ResendActivationEvent
 */
@SuppressWarnings("serial")
public class SendActivationEvent extends ApplicationEvent {

    private final UserDto user;

    private final String appUrl;

    public SendActivationEvent(final UserDto user, final String appUrl) {
		super(user);
        this.user = user;
        this.appUrl = appUrl;
	}

    public UserDto getUser() {
        return this.user;
    }

    public String getAppUrl() {
        return this.appUrl;
    }

}