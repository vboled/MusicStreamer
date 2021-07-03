package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.model.Image;
import vboled.netcracker.musicstreamer.model.User;
import vboled.netcracker.musicstreamer.service.ImageService;

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

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("")
    public ResponseEntity<List<Image>> read() {
        final List<Image> images = imageService.readAll();

        return images != null &&  !images.isEmpty()
                ? new ResponseEntity<>(images, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getImageById(@PathVariable(name = "uuid") String uuid) {
        File imgPath = new File(uploadPath);
            try {
                byte[] image = Files.readAllBytes(Path.of(imgPath.getAbsolutePath() + "/" + uuid + ".jpeg"));
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                headers.setContentLength(image.length);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestParam("file")MultipartFile file) {
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            UUID uuid = UUID.randomUUID();
            String content = file.getContentType();
            String ext = "." + content.substring(content.indexOf('/') + 1);
            String newFileName = uuid.toString() + ext;
            try {
                file.transferTo(new File(uploadDir.getAbsolutePath() + "/" + newFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image res = new Image();
            res.setId(newFileName);
            imageService.create(res);
            return new ResponseEntity<>(uploadDir.getAbsolutePath(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
