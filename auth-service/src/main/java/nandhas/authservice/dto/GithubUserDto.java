package nandhas.authservice.dto;

import java.util.Map;

import nandhas.common.dto.userservice.UserDto;

/**
 * GithubOAuth2User
 */
public class GithubUserDto extends UserDto {

    public GithubUserDto(Map<String, Object> attributes) {
        this.setName((String) attributes.get("name"));
        this.setEmail((String) attributes.get("email"));
        this.setAvatarUrl((String) attributes.get("avatar_url"));
        this.setProvider("Github");
    }

}