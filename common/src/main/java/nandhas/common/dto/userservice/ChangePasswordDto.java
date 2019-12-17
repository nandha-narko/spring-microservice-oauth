package nandhas.common.dto.userservice;

import javax.validation.constraints.NotNull;

import nandhas.common.validator.ValidPassword;

/**
 * ChangePasswordDto
 */
public class ChangePasswordDto {

    private String token;

    @NotNull
    @ValidPassword
    private String password;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}