package nandhas.authservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import nandhas.authservice.dto.CustomUserPrincipal;
import nandhas.authservice.dto.GithubUserDto;
import nandhas.authservice.dto.GoogleUserDto;
import nandhas.common.client.UserServiceClient;
import nandhas.common.dto.userservice.UserDto;
import nandhas.common.exception.ResourceNotFoundException;

/**
 * CustomOAuth2UserService
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserServiceClient userServiceClient;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        UserDto userDto = this.saveUser(oAuth2UserRequest, oAuth2User);

        return new CustomUserPrincipal(userDto, oAuth2User.getAttributes());
    }

    private UserDto saveUser(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        UserDto oauthUser = this.getUser(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User);

        try {
            UserDto userDto = userServiceClient.getUserByEmail(oauthUser.getEmail());
            userDto.setAvatarUrl(oauthUser.getAvatarUrl());
            userDto.setName(oauthUser.getName());
            userDto = userServiceClient.updateUser(userDto);

            return userDto;
        } catch (ResourceNotFoundException ex) {
            oauthUser.setActive(true);
            oauthUser.setRoles(Arrays.asList("USER"));
            oauthUser = userServiceClient.createUser(oauthUser);

            return oauthUser;
        }
    }

    private UserDto getUser(String registrationId, OAuth2User oAuth2User) {
        if (registrationId.equals("google")) {
            return new GoogleUserDto(oAuth2User.getAttributes());
        } else {
            return new GithubUserDto(oAuth2User.getAttributes());
        }
    }
}