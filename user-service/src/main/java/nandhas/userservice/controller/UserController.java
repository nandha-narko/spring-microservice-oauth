package nandhas.userservice.controller;

import java.util.HashMap;

import nandhas.common.dto.userservice.UserDto;
import nandhas.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Component
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable String id) {
        return this.userService.getUser(id);
    }

    @RequestMapping(value =  "/user", params = "email", method = RequestMethod.GET)
    public UserDto getUserByEmail(@RequestParam("email") String email) {
        return this.userService.getUserByEmail(email);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return this.userService.createUser(userDto);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return this.userService.updateUser(userDto);
    }

    @RequestMapping(value = "/user/{id}/avatar", method = RequestMethod.GET)
    public byte[] getAvatar(@PathVariable String id) {
        return this.userService.getAvatar(id);
    }

    @RequestMapping(value = "/user/{id}/avatar", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateAvatar(@PathVariable String id,  @RequestPart("avatar") MultipartFile avatar) {
        return this.userService.updateAvatar(id, avatar);
    }
}