package nandhas.common.dto.userservice;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import nandhas.common.validator.ValidPassword;

/**
 * UserDto
 */
public class UserDto {

    private String userId;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    @ValidPassword
    private String password;
    private Boolean active;
    private String avatarUrl;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActive() {
        return this.active;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
    
}