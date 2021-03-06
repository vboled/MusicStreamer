package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;
import vboled.netcracker.musicstreamer.model.Song;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.model.validator.AudioValidator;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;
import vboled.netcracker.musicstreamer.service.FileService;
import vboled.netcracker.musicstreamer.service.SongService;

import javax.security.auth.DestroyFailedException;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/songs")
public class SongController {

    private final FileValidator fileValidator;
    private final ApplicationConfiguration.FileConfiguration fileConfiguration;
    private final FileService fileService;

    private final SongService songService;

    public SongController(FileService fileService, SongService songService,
                          ApplicationConfiguration applicationConfiguration) {
        this.fileService = fileService;
        this.songService = songService;
        this.fileConfiguration = applicationConfiguration.getFileConfiguration();
        this.fileValidator = new AudioValidator(fileConfiguration);
    }

    void checkAdminOrOwnerPerm(User user, Long id) throws IllegalAccessError {
        Set<Permission> perm = user.getRole().getPermissions();
        if (!(perm.contains(Permission.ADMIN_PERMISSION) ||
                (perm.contains(Permission.OWNER_PERMISSION) &&
                        user.getId().equals(songService.getById(id).getOwnerID())))) {
            throw new IllegalAccessError();
        }
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> readAll() {
        final List<Song> songs = songService.readAll();
        if (songs == null || songs.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/audio/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> readFileSong(@AuthenticationPrincipal User user,
                                          @RequestParam Long id) {
        try {
            checkAdminOrOwnerPerm(user, id);
            Song song = songService.getById(id);
            if (!song.isAvailable())
                return new ResponseEntity<>("File song is unavailable", HttpStatus.NOT_FOUND);
            return fileService.read(song.getUuid(), fileValidator);
        } catch (IOException e) {
            return new ResponseEntity<>("File song not found", HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> readSong(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(songService.getById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> upload(@AuthenticationPrincipal User user,
                                    @RequestParam Long id, @RequestParam MultipartFile file) {
        try{
            checkAdminOrOwnerPerm(user, id);
            String name = fileService.uploadFile(file, fileValidator, UUID.randomUUID().toString());
            return new ResponseEntity<>(songService.setSongFile(id, name), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>("Album not found", HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> create(@AuthenticationPrincipal User user,
                                    @RequestBody Song song) {
        try {
            if (user.getRole().getPermissions().contains(Permission.OWNER_PERMISSION)) {
                song.setOwnerID(user.getId());
            }
            songService.create(song);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @DeleteMapping("/audio/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> deleteAudio(@AuthenticationPrincipal User user,
                                       @RequestParam Long id) {
        try {
            checkAdminOrOwnerPerm(user, id);
            Song song = songService.getById(id);
            if (song.getUuid() == null)
                return new ResponseEntity<>(HttpStatus.OK);
            fileService.delete(song.getUuid(), fileValidator);
            songService.deleteAudio(song);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        } catch (DestroyFailedException e) {
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,
                                    @RequestParam Long id) {
        try{
            checkAdminOrOwnerPerm(user, id);
            ResponseEntity<?> res = deleteAudio(user, id);
            if (res.getStatusCode() != HttpStatus.OK)
                return res;
            Song song = songService.getById(id);
            songService.delete(song);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/audio/update/{id}")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    ResponseEntity<?> updateSongFile(@AuthenticationPrincipal User user,
                                       @PathVariable Long id, @RequestParam MultipartFile file) {
        ResponseEntity<?> res = deleteAudio(user, id);
        if (!res.getStatusCode().equals(HttpStatus.OK))
            return res;
        return upload(user, id, file);
    }

    @PutMapping("/song/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> update(@AuthenticationPrincipal User user,
                                    @RequestBody Song song) {
        try{
            Song res;
            Set<Permission> perm = user.getRole().getPermissions();
            if (perm.contains(Permission.ADMIN_PERMISSION)) {
                res = songService.fullUpdateSong(song);
            } else if (perm.contains(Permission.OWNER_PERMISSION) &&
                       user.getId().equals(songService.getById(song.getId()).getOwnerID())) {
                res = songService.partialUpdateSong(song);
            }
            else
                return new ResponseEntity<>("You don't have permission!!!", HttpStatus.NOT_MODIFIED);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Song not found", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
