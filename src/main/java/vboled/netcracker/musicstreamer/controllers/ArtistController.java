package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.ArtistService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/artist")
public class ArtistController {
    private final ArtistService artistService;
    private final User user;

    public ArtistController(ArtistService artistService, User user) {
        this.artistService = artistService;
        this.user = user;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> createArtist(@RequestBody Artist artist) {
        try {
            artistService.create(artist);
            return new ResponseEntity<>(artist, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> getAllArtists() {
        final List<Artist> artists = artistService.readAll();
        if (artists == null || artists.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> getArtist(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(artistService.getById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Artist not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    ResponseEntity<?> updateArtist(/*@AuthenticationPrincipal User user,*/
                                  @RequestBody Artist artist) {
        try{
            Artist res = null;
            Set<Permission> perm = user.getRole().getPermissions();
            if (perm.contains(Permission.ADMIN_PERMISSION)) {
                res = artistService.fullUpdateArtist(artist);
            } else if (perm.contains(Permission.OWNER_PERMISSION) &&
                    user.getId().equals(artistService.getById(artist.getOwnerID()))) {
                res = artistService.partialUpdateArtist(artist);
            }
            else
                return new ResponseEntity<>("You don't have permission!!!", HttpStatus.NOT_MODIFIED);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Artist not found", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> delete(/*@AuthenticationPrincipal User user,*/
                                    @RequestParam Long id) {
        try{
            if (user.getRole().getPermissions().contains(Permission.OWNER_PERMISSION) &&
                    user.getId().equals(artistService.getById(id).getOwnerID())) {
                return new ResponseEntity<>("You don't have permission!!!", HttpStatus.NOT_MODIFIED);
            }
            artistService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
