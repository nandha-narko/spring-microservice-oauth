package nandhas.userservice.controller;

import nandhas.common.dto.userservice.TokenDto;
import nandhas.userservice.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TokenController
 */
@RestController
public class TokenController {

    @Autowired
    TokenService tokenService;

    @GetMapping("/token/{token}")
    public TokenDto getToken(@PathVariable String token) {
        return tokenService.getToken(token);
    }

    @PostMapping("/token")
    public TokenDto createToken(@RequestBody TokenDto tokenDto) {
        return tokenService.createToken(tokenDto); 
    }
    
}