package nandhas.authservice.dto;

import java.util.Map;

import nandhas.common.dto.userservice.UserDto;

/**
 * GoogleOAuth2User
 */
public class GoogleUserDto extends UserDto {

    public GoogleUserDto(Map<String, Object> attributes) {
        this.setName((String) attributes.get("name"));
        this.setEmail((String) attributes.get("email"));
        this.setAvatarUrl((String) attributes.get("picture"));
        this.setProvider("Google");
    }
    
}