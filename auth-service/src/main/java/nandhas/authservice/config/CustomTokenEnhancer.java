package nandhas.authservice.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import nandhas.authservice.dto.CustomUserPrincipal;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		final Map<String, Object> additionalInfo = new HashMap<>();
		CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
		additionalInfo.put("user_id", userPrincipal.getId());
		additionalInfo.put("user_name", userPrincipal.getName());
		additionalInfo.put("user_avatar", userPrincipal.getAvatar());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
	}

}