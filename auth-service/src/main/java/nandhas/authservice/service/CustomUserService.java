package nandhas.authservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import nandhas.authservice.dto.CustomUserPrincipal;
import nandhas.common.client.UserServiceClient;
import nandhas.common.dto.userservice.UserDto;
import nandhas.common.exception.ResourceNotFoundException;

@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserServiceClient userServiceClient;

	@Autowired
	TokenStore tokenStore;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		try {
			final UserDto userDto = userServiceClient.getUserByEmail(username);
			return new CustomUserPrincipal(userDto);
		} catch (ResourceNotFoundException ex) {
			throw new UsernameNotFoundException(username);
		}
	}

	public UserDto getUser(final String email) {
		try {
			return userServiceClient.getUserByEmail(email);
		} catch (final ResourceNotFoundException ex) {
			return null;
		}
	}

	public UserDto createUser(final UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userDto.setActive(false);
		userDto.setRoles(Arrays.asList("USER"));
		return this.userServiceClient.createUser(userDto);
	}

	public boolean activateUser(final String userId) {
		final UserDto userDto = userServiceClient.getUser(userId);
		if (userDto == null) {
			throw new UsernameNotFoundException("");
		} else {
			userDto.setActive(true);
			userServiceClient.updateUser(userDto);
		}

		return true;
	}

	public boolean changePassword(final String userId, final String password) {
		final UserDto userDto = userServiceClient.getUser(userId);
		if (userDto == null) {
			throw new UsernameNotFoundException("");
		} else {
			userDto.setPassword(passwordEncoder.encode(password));
			userServiceClient.updateUser(userDto);
		}

		return true;
	}
}