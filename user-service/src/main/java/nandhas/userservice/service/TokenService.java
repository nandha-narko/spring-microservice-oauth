package nandhas.userservice.service;

import nandhas.common.dto.userservice.TokenDto;

/**
 * TokenService
 */
public interface TokenService {

    TokenDto getToken(String token);

    TokenDto createToken(TokenDto tokenDto);
}