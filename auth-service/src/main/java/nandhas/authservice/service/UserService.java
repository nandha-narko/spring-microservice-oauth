package nandhas.authservice.service;

import java.util.Arrays;

import nandhas.common.client.UserServiceClient;
import nandhas.common.dto.userservice.UserDto;
import nandhas.common.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserServiceClient userServiceClient;

	@Autowired
	TokenStore tokenStore;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final UserDto user = userServiceClient.getUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(),
				user.isActive(), true, true, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
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
		}
		else {
			userDto.setPassword(passwordEncoder.encode(password));
			userServiceClient.updateUser(userDto);
		}
		
		return true;
	}
}