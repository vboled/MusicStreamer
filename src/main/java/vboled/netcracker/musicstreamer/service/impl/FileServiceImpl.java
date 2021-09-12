package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;
import vboled.netcracker.musicstreamer.service.FileService;

import javax.security.auth.DestroyFailedException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public ResponseEntity<?> read(String uuid, FileValidator fileValidator) throws IOException {
        byte[] image = Files.readAllBytes(Path.of(fileValidator.getPath() + "/" + uuid));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", fileValidator.getContentType(uuid));
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @Override
    public String uploadFile(MultipartFile file, FileValidator fileValidator, String uuid) throws IllegalArgumentException, IOException {
        if (file == null)
            throw new IllegalArgumentException("File is null");

        if (fileValidator.getMaxSize() < file.getSize())
            throw new IllegalArgumentException("File is too large");

        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFileName;

        if (!fileValidator.getExtensions().contains(ext))
            throw new IllegalArgumentException("Wrong file extension");

        File uploadDir = new File(fileValidator.getPath());
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        newFileName = uuid + ext;

        file.transferTo(new File(uploadDir.getAbsolutePath() + "/" + newFileName));
        return newFileName;
    }

    @Override
    public void delete(String uuid, FileValidator fileValidator) throws DestroyFailedException {
        File file = new File(fileValidator.getPath() + "/" + uuid);
        if (!file.delete())
            throw new DestroyFailedException();
    }
}
