package nandhas.common.client;

import java.util.HashMap;

import nandhas.common.dto.userservice.TokenDto;
import nandhas.common.dto.userservice.UserDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;


@FeignClient("user-service")
public interface UserServiceClient {
    
    @GetMapping("/user/{id}")
    UserDto getUser(@PathVariable String id);

    @GetMapping("/user")
    UserDto getUserByEmail(@RequestParam String email);

    @PostMapping(value="/user")
    UserDto createUser(@RequestBody UserDto userDto);

    @PutMapping(value="/user")
    UserDto updateUser(@RequestBody UserDto userDto);

    @GetMapping("/token/{token}")
    TokenDto getToken(@PathVariable String token);
    
    @PostMapping(value="/token")
    TokenDto createToken(@RequestBody TokenDto tokenDto);

    @GetMapping("/user/{id}/avatar")
    byte[] getAvatar(@PathVariable String id);

    @PostMapping(value = "/user/{id}/avatar")
    String updateAvatar(@PathVariable String id, @RequestPart("avatar") MultiValueMap<String, Object> avatar);
}