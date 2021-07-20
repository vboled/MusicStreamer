package vboled.netcracker.musicstreamer.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.service.FileControllerServiceImpl;
import vboled.netcracker.musicstreamer.service.SongService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;


    private final Set<String> imageExt = new HashSet<String>(Arrays.asList(".mp3",".ogg", ".wav"));

    @Value("${audio.storage.dir}")
    private String audioDir;

    @Value("${file.storage.path}")
    private String uploadPath;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> readAll() {
        final List<Song> songs = songService.readAll();
        if (songs == null || songs.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> read(@PathVariable(name = "uuid") String uuid) {
        return FileControllerServiceImpl.read(uuid, uploadPath + "/" + audioDir);
    }

    @PostMapping("/upload/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        return FileControllerServiceImpl.uploadFile(file, imageExt, uploadPath + "/" + audioDir);
    }

    @PostMapping("/create/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> create(@RequestBody Song song) {
        if (read(song.getUuid()).getStatusCode().equals(HttpStatus.BAD_REQUEST))
            return new ResponseEntity<>("Wrong file name!", HttpStatus.BAD_REQUEST);
        try {
            songService.create(song);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> delete(@PathVariable(name = "uuid") String uuid) {
        ResponseEntity<?> res = FileControllerServiceImpl.delete(uuid, uploadPath + "/" + audioDir);
        if (!res.getStatusCode().equals(HttpStatus.OK))
            return res;
        if (songService.delete(uuid))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> update(@PathVariable(name = "uuid") String uuid,
                                    @RequestParam("file") MultipartFile file) {
//        if (delete(uuid).getStatusCode().equals(HttpStatus.OK))
//            return create(file);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
