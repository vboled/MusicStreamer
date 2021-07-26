package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.AlbumService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }


    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> createAlbum(@RequestBody Album album) {
        try {
            albumService.create(album);
            return new ResponseEntity<>(album, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> getAllAlbums() {
        final List<Album> albums = albumService.readAll();
        if (albums == null || albums.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> getAlbum(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(albumService.getById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Album not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> updateAlbum(@AuthenticationPrincipal User user,
                                  @RequestBody Album album) {
        try{
            Album res = null;
            Set<Permission> perm = user.getRole().getPermissions();
            if (perm.contains(Permission.ADMIN_PERMISSION)) {
                res = albumService.fullUpdateAlbum(album);
            } else if (perm.contains(Permission.OWNER_PERMISSION) &&
                    user.getId().equals(albumService.getById(album.getOwnerID()))) {
                res = albumService.partialUpdateAlbum(album);
            }
            else
                return new ResponseEntity<>("You don't have permission!!!", HttpStatus.NOT_MODIFIED);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Album not found", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
