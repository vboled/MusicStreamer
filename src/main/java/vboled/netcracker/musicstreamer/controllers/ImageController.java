package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.service.impl.FileControllerServiceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final Set<String> imageExt = new HashSet<String>(Arrays.asList(".gif", ".png", ".jpeg", ".jpg"));

    // Путь к директории с файлами
    @Value("${file.storage.path}")
    private String uploadPath;

    // Название субдиректории
    @Value("${image.storage.dir}")
    private String imageDir;

    @Value("${max.byte.image.size}")
    private int maxImageSize;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> read(@PathVariable(name = "uuid") String uuid) {
        return FileControllerServiceImpl.read(uuid, uploadPath + "/" + imageDir);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file) {
        if (file.getSize() > maxImageSize)
            return new ResponseEntity<>("File size exceeds 5 MB", HttpStatus.BAD_REQUEST);
        return FileControllerServiceImpl.uploadFile(file, imageExt, uploadPath + "/" + imageDir,
                UUID.randomUUID().toString());
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> delete(@PathVariable(name = "uuid") String uuid) {
        return FileControllerServiceImpl.delete(uuid, uploadPath + "/" + imageDir);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> update(@PathVariable(name = "uuid") String uuid,
                                    @RequestParam("file") MultipartFile file) {
        if (file.getSize() > maxImageSize)
            return new ResponseEntity<>("File size exceeds 5 MB", HttpStatus.BAD_REQUEST);
        if (delete(uuid).getStatusCode().equals(HttpStatus.OK))
            return create(file);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
