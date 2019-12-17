package nandhas.userservice.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import nandhas.common.dto.userservice.UserDto;
import nandhas.common.exception.ResourceNotFoundException;
import nandhas.common.exception.UserAlreadyExistException;
import nandhas.common.utils.MapperUtil;
import nandhas.userservice.model.Upload;
import nandhas.userservice.model.User;
import nandhas.userservice.repository.UploadRepository;
import nandhas.userservice.repository.UserRepository;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DefaultUserService implements UserService {

	@Autowired
	UserRepository repository;

	@Autowired
	UploadRepository uploadRepository;

	@Override
	public UserDto getUser(String userId) {
		Optional<User> user = repository.findById(userId);
		if (!user.isPresent()) {
			throw new ResourceNotFoundException("User not found");
		}
		return MapperUtil.Convert(user.get(), UserDto.class);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		User user = repository.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("User not found");
		}
		return MapperUtil.Convert(user, UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = repository.findByEmail(userDto.getEmail());
		if (user != null) {
			throw new UserAlreadyExistException();
		}
		user = MapperUtil.Convert(userDto, User.class);
		return MapperUtil.Convert(repository.save(user), UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		User user = MapperUtil.Convert(userDto, User.class);
		return MapperUtil.Convert(repository.save(user), UserDto.class);
	}

	@Override
	public byte[] getAvatar(String id) {
		Optional<Upload> upload = uploadRepository.findById(id);

		if(upload.isPresent()) {
			return upload.get().getUpload().getData();
		}

		return null;
	}

	@Override
	public String updateAvatar(String userId, MultipartFile avatar) {
		Upload upload = new Upload();
		
		try {
			upload.setUpload(new Binary(BsonBinarySubType.BINARY, avatar.getBytes()));
			upload = uploadRepository.save(upload);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return upload.getUploadId();
	}

}