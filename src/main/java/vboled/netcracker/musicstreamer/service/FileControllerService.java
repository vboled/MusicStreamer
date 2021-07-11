package vboled.netcracker.musicstreamer.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface FileControllerService {

    ResponseEntity<?> read(String uuid, String path);

    ResponseEntity<?> uploadFile(MultipartFile file, Set<String> extensions, String path);

    ResponseEntity<?> delete(String uuid, String path);
}
