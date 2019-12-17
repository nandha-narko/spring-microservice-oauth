package nandhas.authservice.event;

import nandhas.common.dto.userservice.UserDto;

import org.springframework.context.ApplicationEvent;

/**
 * ResetPasswordEvent
 */
@SuppressWarnings("serial")
public class ResetPasswordEvent extends ApplicationEvent {

    private final UserDto user;

    private final String appUrl;

	public ResetPasswordEvent(final UserDto user, final String appUrl) {
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