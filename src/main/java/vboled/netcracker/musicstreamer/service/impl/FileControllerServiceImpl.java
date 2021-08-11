package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileControllerServiceImpl {

    public static ResponseEntity<?> read(String uuid, FileValidator fileValidator) {
        try {
            byte[] image = Files.readAllBytes(Path.of(fileValidator.getPath() + "/" + uuid));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", fileValidator.getContentType(uuid));
            headers.setContentLength(image.length);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File not found!!!", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<?> uploadFile(MultipartFile file, FileValidator fileValidator, String uuid) {
        if (file == null)
            return new ResponseEntity<>("File is null", HttpStatus.BAD_REQUEST);

        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFileName;

        if (!fileValidator.getExtensions().contains(ext))
            return new ResponseEntity<>("Wrong file extension", HttpStatus.BAD_REQUEST);

        File uploadDir = new File(fileValidator.getPath());
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        newFileName = uuid + ext;

        try {
            file.transferTo(new File(uploadDir.getAbsolutePath() + "/" + newFileName));
        } catch (IOException e) {
            return new ResponseEntity<>("File wasn't uploaded", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newFileName, HttpStatus.CREATED);
    }

    public static ResponseEntity<?> delete(String uuid, FileValidator fileValidator) {
        File file = new File(fileValidator.getPath() + "/" + uuid);
        if (file.delete())
            return  new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
