package vboled.netcracker.musicstreamer.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;

import javax.security.auth.DestroyFailedException;
import java.io.IOException;

public interface FileService {

    ResponseEntity<?> read(String uuid, FileValidator fileValidator) throws IOException;

    String uploadFile(MultipartFile file, FileValidator fileValidator, String uuid) throws IOException;

    void delete(String uuid, FileValidator fileValidator) throws DestroyFailedException;
}
