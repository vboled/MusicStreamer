package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.model.validator.AudioValidator;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;
import vboled.netcracker.musicstreamer.service.impl.FileControllerServiceImpl;
import vboled.netcracker.musicstreamer.service.SongService;

import java.util.*;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final FileValidator fileValidator = new AudioValidator();

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> readAll() {
        final List<Song> songs = songService.readAll();
        if (songs == null || songs.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/file/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> readFileSong(@RequestParam String uuid) {
        return FileControllerServiceImpl.read(uuid, fileValidator);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> readSong(@RequestParam String uuid) {
        try {
            return new ResponseEntity<>(songService.read(uuid), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        return FileControllerServiceImpl.uploadFile(file, fileValidator, UUID.randomUUID().toString());
    }

    @PostMapping("/create/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> create(@RequestBody Song song) {
        if (readFileSong(song.getUuid()).getStatusCode().equals(HttpStatus.BAD_REQUEST))
            return new ResponseEntity<>("Wrong file name!", HttpStatus.BAD_REQUEST);
        try {
            songService.create(song);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,
                                    @RequestParam String uuid) {
        try{
            if (user.getRole().getPermissions().contains(Permission.OWNER_PERMISSION) &&
                    user.getId().equals(songService.read(uuid).getOwnerID())) {
                return new ResponseEntity<>("You don't have permission!!!", HttpStatus.NOT_MODIFIED);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        }
        ResponseEntity<?> res = FileControllerServiceImpl.delete(uuid, fileValidator);
        if (!res.getStatusCode().equals(HttpStatus.OK))
            return res;
        if (songService.delete(uuid))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/file/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> updateFile(@RequestParam("uuid") String uuid,
                                        @RequestParam("file") MultipartFile file) {
        ResponseEntity<?> res = FileControllerServiceImpl.delete(uuid, fileValidator);
        if (!res.getStatusCode().equals(HttpStatus.OK))
            return res;
        return FileControllerServiceImpl.uploadFile(file, fileValidator, uuid);
    }

    @PutMapping("/song/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> update(@AuthenticationPrincipal User user,
                                    @RequestBody Song song) {
        try{
            Song res = null;
            Set<Permission> perm = user.getRole().getPermissions();
            if (perm.contains(Permission.ADMIN_PERMISSION)) {
                res = songService.fullUpdateSong(song);
            } else if (perm.contains(Permission.OWNER_PERMISSION) &&
                       user.getId().equals(songService.read(song.getUuid()).getOwnerID())) {
                res = songService.partialUpdateSong(song);
            }
            else
                return new ResponseEntity<>("You don't have permission!!!", HttpStatus.NOT_MODIFIED);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
