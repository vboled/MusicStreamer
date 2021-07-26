package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

@Service
public class FileControllerServiceImpl {

    private static String getContentType(String uuid) {
        String ext = uuid.substring(uuid.lastIndexOf('.'));
        if (ext.equals(".jpeg") || ext.equals(".jpg"))
            return "image/jpeg";
        else if (ext.equals(".png"))
            return "image/png";
        else if (ext.equals(".gif"))
            return "image/gif";
        else if (ext.equals(".mp3"))
            return "audio/mpeg";
        else if (ext.equals(".wav"))
            return "audio/wav";
        else if (ext.equals(".ogg"))
            return "audio/ogg";
        throw new IllegalArgumentException("Wrong file ext.");
    }

    public static ResponseEntity<?> read(String uuid, String path) {
        try {
            byte[] image = Files.readAllBytes(Path.of(path + "/" + uuid));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", getContentType(uuid));
            headers.setContentLength(image.length);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
        }
        return new ResponseEntity<>("File not found!!!", HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> uploadFile(MultipartFile file, Set<String> extensions, String path, String uuid) {
        if (file == null)
            return new ResponseEntity<>("File is null", HttpStatus.BAD_REQUEST);

        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFileName;

        if (!extensions.contains(ext))
            return new ResponseEntity<>("Wrong file extension", HttpStatus.BAD_REQUEST);

        File uploadDir = new File(path);
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

    public static ResponseEntity<?> delete(String uuid, String path) {
        File file = new File(path + "/" + uuid);
        if (file.delete())
            return  new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
