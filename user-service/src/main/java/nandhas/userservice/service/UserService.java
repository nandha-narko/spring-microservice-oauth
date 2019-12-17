package nandhas.userservice.service;

import org.springframework.web.multipart.MultipartFile;

import nandhas.common.dto.userservice.UserDto;

public interface UserService {

    UserDto getUser(String userId);

    UserDto getUserByEmail(String email);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user);

    String updateAvatar(String userId, MultipartFile avatar);

	byte[] getAvatar(String id);
}