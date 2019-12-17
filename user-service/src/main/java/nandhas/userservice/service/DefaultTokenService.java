package nandhas.userservice.service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import nandhas.common.dto.userservice.TokenDto;
import nandhas.common.utils.MapperUtil;
import nandhas.userservice.model.Token;
import nandhas.userservice.repository.TokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DefaultTokenService
 */
@Service
public class DefaultTokenService implements TokenService {

    @Autowired
    TokenRepository repository;

    @Override
    public TokenDto getToken(String token) {
        return MapperUtil.Convert(repository.findByToken(token), TokenDto.class);
    }

    @Override
    public TokenDto createToken(TokenDto tokenDto) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        tokenDto.setToken(UUID.randomUUID().toString());
        tokenDto.setExpiresAt(calendar.getTime());
        Token token = MapperUtil.Convert(tokenDto, Token.class);
        return MapperUtil.Convert(repository.save(token), TokenDto.class);
    }

}