package nandhas.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import nandhas.userservice.model.Upload;

/**
 * UploadRepository
 */
public interface UploadRepository extends MongoRepository<Upload, String> {

    
}