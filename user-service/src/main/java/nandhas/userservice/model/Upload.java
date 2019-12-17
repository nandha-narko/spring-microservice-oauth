package nandhas.userservice.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

/**
 * Uploads
 */
public class Upload {

    @Id
    private String uploadId;

    private Binary upload;

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public Binary getUpload() {
        return this.upload;
    }

    public void setUpload(Binary upload) {
        this.upload = upload;
    }

}