package nandhas.userservice.repository;

import nandhas.userservice.model.Token;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * UserTokenRepository
 */
public interface TokenRepository extends MongoRepository<Token, String>  {

    public Token findByToken(String token);
}