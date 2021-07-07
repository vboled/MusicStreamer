package vboled.netcracker.musicstreamer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

@Service
public class FileControllerService {

    private MediaType getContentType(String uuid) {
        String ext = uuid.substring(uuid.lastIndexOf('.'));
        if (ext.equals(".jpeg") || ext.equals(".jpg"))
            return MediaType.IMAGE_JPEG;
        else if (ext.equals(".png"))
            return MediaType.IMAGE_PNG;
        else if (ext.equals(".gif"))
            return MediaType.IMAGE_GIF;
        else if (ext.equals(".mp3") || ext.equals(".wav") || ext.equals(".flac") || ext.equals(".ogg"))
            return MediaType.APPLICATION_OCTET_STREAM;
        throw new IllegalArgumentException("Wrong file ext.");
    }

    public ResponseEntity<?> read(String uuid, String path) {
        try {
            byte[] image = Files.readAllBytes(Path.of(path + "/" + uuid));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(getContentType(uuid));
            headers.setContentLength(image.length);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> uploadFile(MultipartFile file, Set<String> extensions, String path) {
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
        newFileName = UUID.randomUUID().toString() + ext;

        try {
            file.transferTo(new File(uploadDir.getAbsolutePath() + "/" + newFileName));
        } catch (IOException e) {
            return new ResponseEntity<>("File wasn't uploaded", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newFileName, HttpStatus.CREATED);
    }

    public ResponseEntity<?> delete(String uuid, String path) {
        File file = new File(path + "/" + uuid);
        if (file.delete())
            return  new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
