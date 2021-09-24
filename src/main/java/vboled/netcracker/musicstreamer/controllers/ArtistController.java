package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;
import vboled.netcracker.musicstreamer.view.ArtistView;
import vboled.netcracker.musicstreamer.model.Artist;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;
import vboled.netcracker.musicstreamer.model.validator.ImageValidator;
import vboled.netcracker.musicstreamer.service.AlbumService;
import vboled.netcracker.musicstreamer.service.ArtistService;
import vboled.netcracker.musicstreamer.service.SongService;
import vboled.netcracker.musicstreamer.service.impl.FileServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/artist")
public class ArtistController {
    private final ArtistService artistService;
    private final SongService songService;
    private final AlbumService albumService;
    private final ApplicationConfiguration.FileConfiguration fileConfiguration;
    private final FileServiceImpl fileService;
    private final FileValidator fileValidator;

    public ArtistController(ArtistService artistService, SongService songService, AlbumService albumService,
                            FileServiceImpl fileService, ApplicationConfiguration applicationConfiguration) {
        this.artistService = artistService;
        this.songService = songService;
        this.albumService = albumService;
        this.fileService = fileService;
        this.fileConfiguration = applicationConfiguration.getFileConfiguration();
        fileValidator = new ImageValidator(fileConfiguration);
    }

    void checkAdminOrOwnerPerm(User user, Long id) throws IllegalAccessError {
        Set<Permission> perm = user.getRole().getPermissions();
        if (!(perm.contains(Permission.ADMIN_PERMISSION) ||
                (perm.contains(Permission.OWNER_PERMISSION) &&
                        user.getId().equals(artistService.getById(id).getOwnerID())))) {
            throw new IllegalAccessError();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
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
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> getArtist(@AuthenticationPrincipal User user, @RequestParam Long id) {
        try {
            Artist artist = artistService.getById(id);
            ArtistView artistView = new ArtistView(artist,
                    albumService.getByArtist(artist), songService.getByArtist(artist, user));
            return new ResponseEntity<>(artistView, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Artist not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cover/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    ResponseEntity<?> uploadArtistCover(@AuthenticationPrincipal User user,
            @RequestParam Long id, @RequestParam MultipartFile file) {
        try{
            checkAdminOrOwnerPerm(user, id);
            String name = fileService.uploadFile(file, fileValidator, UUID.randomUUID().toString());
            return new ResponseEntity<>(artistService.setCover(id, name), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        } catch (IOException | NoSuchElementException e) {
            return new ResponseEntity<>("Artist not found", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    ResponseEntity<?> updateArtist(@AuthenticationPrincipal User user,
                                  @RequestBody Artist artist) {
        try{
            Artist res = null;
            Set<Permission> perm = user.getRole().getPermissions();
            if (perm.contains(Permission.ADMIN_PERMISSION)) {
                res = artistService.fullUpdateArtist(artist);
            } else if (perm.contains(Permission.OWNER_PERMISSION) &&
                    user.getId().equals(artistService.getById(artist.getId()).getOwnerID())) {
                res = artistService.partialUpdateArtist(artist);
            }
            else
                return new ResponseEntity<>("You don't have permission!!!", HttpStatus.NOT_MODIFIED);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Artist not found", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,
                                    @RequestParam Long id) {
        try{
            if (!(user.getRole().getPermissions().contains(Permission.OWNER_PERMISSION) &&
                    user.getId().equals(artistService.getById(id).getOwnerID()))) {
                return new ResponseEntity<>("You don't have permission!!!", HttpStatus.NOT_MODIFIED);
            }
            Artist artist = artistService.getById(id);
            artistService.delete(artist);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
