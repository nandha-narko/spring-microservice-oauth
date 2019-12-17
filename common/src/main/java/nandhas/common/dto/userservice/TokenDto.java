package nandhas.common.dto.userservice;

import java.util.Date;

import nandhas.common.enums.TokenType;

/**
 * TokenDto
 */
public class TokenDto {

    private String tokenId; 

    private String token;

    private Date expiresAt;

    private TokenType type;

    private String userId;

    public TokenDto() {

    }

    public TokenDto(TokenType type, String userId) {
        this.type = type;
        this.userId = userId;
    }

    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public TokenType getType() {
        return this.type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
            " tokenId='" + getTokenId() + "'" +
            ", token='" + getToken() + "'" +
            ", expiresAt='" + getExpiresAt() + "'" +
            ", type='" + getType() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }

}