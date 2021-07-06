package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Value("${image.storage.path}")
    private String uploadPath;

    @GetMapping("/{uuid}")
    public ResponseEntity<?> read(@PathVariable(name = "uuid") String uuid) {
        try {
            byte[] image = Files.readAllBytes(Path.of(uploadPath + "/" + uuid));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(getContentType(uuid));
            headers.setContentLength(image.length);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private MediaType getContentType(String uuid) {
        String ext = uuid.substring(uuid.lastIndexOf('.'));
        if (ext.equals(".jpeg"))
            return MediaType.IMAGE_JPEG;
        else if (ext.equals(".png"))
            return MediaType.IMAGE_PNG;
        else if (ext.equals(".gif"))
            return MediaType.IMAGE_GIF;
        throw new IllegalArgumentException("Wrong file ext.");
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestParam("file")MultipartFile file) {
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String content = file.getContentType();
            String ext = "." + content.substring(content.indexOf('/') + 1);
            String newFileName = UUID.randomUUID().toString() + ext;
            try {
                file.transferTo(new File(uploadDir.getAbsolutePath() + "/" + newFileName));
            } catch (IOException e) {
                e.printStackTrace();
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(newFileName, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Wrong file", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable(name = "uuid") String uuid) {
        File file = new File(uploadPath + "/" + uuid);
        if (file.delete())
            return  new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable(name = "uuid") String uuid,
                                    @RequestParam("file")MultipartFile file) {
        ResponseEntity delResult = delete(uuid);
        if (delResult.getStatusCode().equals(HttpStatus.OK))
            return create(file);
        return delResult;
    }
}
