package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.service.FileControllerService;
import vboled.netcracker.musicstreamer.service.FileControllerServiceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final FileControllerService fileControllerService = new FileControllerServiceImpl();

    private final Set<String> imageExt = new HashSet<String>(Arrays.asList(".mp3",".ogg", ".wav"));

    @Value("${audio.storage.dir}")
    private String audioDir;

    @Value("${file.storage.path}")
    private String uploadPath;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> read(@PathVariable(name = "uuid") String uuid) {
        return fileControllerService.read(uuid, uploadPath + "/" + audioDir);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file) {
        return fileControllerService.uploadFile(file, imageExt, uploadPath + "/" + audioDir);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> delete(@PathVariable(name = "uuid") String uuid) {
        return fileControllerService.delete(uuid, uploadPath + "/" + audioDir);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> update(@PathVariable(name = "uuid") String uuid,
                                    @RequestParam("file") MultipartFile file) {
        if (delete(uuid).getStatusCode().equals(HttpStatus.OK))
            return create(file);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
