package nandhas.userservice.model;

import java.util.Date;

import nandhas.common.enums.TokenType;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class Token {
	
	@Id
    private String tokenId;
	
	private String token;

	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date expiresAt;
	
	private TokenType type;
	
	private String userId;

	public Token() {
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
