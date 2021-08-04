package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vboled.netcracker.musicstreamer.model.Album;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.model.validator.FileValidator;
import vboled.netcracker.musicstreamer.model.validator.ImageValidator;
import vboled.netcracker.musicstreamer.service.AlbumService;
import vboled.netcracker.musicstreamer.service.impl.FileControllerServiceImpl;
import vboled.netcracker.musicstreamer.service.impl.FileServiceImpl;

import javax.security.auth.DestroyFailedException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/album")
public class AlbumController {

    private final FileValidator fileValidator = new ImageValidator();

    private final AlbumService albumService;

    private final FileServiceImpl fileService;

    public AlbumController(AlbumService albumService, FileServiceImpl fileService) {
        this.albumService = albumService;
        this.fileService = fileService;
    }

    void checkAdminOrOwnerPerm(User user, Long id) throws IllegalAccessError {
        Set<Permission> perm = user.getRole().getPermissions();
        if (!(perm.contains(Permission.ADMIN_PERMISSION) ||
                (perm.contains(Permission.OWNER_PERMISSION) &&
                        user.getId().equals(albumService.getById(id).getOwnerID())))) {
            throw new IllegalAccessError();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    ResponseEntity<?> createAlbum(@AuthenticationPrincipal User user,
                                  @RequestBody Album album) {
        try {
            if (user.getRole().getPermissions().contains(Permission.OWNER_PERMISSION)) {
                album.setOwnerID(user.getId());
            }
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

    @GetMapping("/cover/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> getAlbumCover(@AuthenticationPrincipal User user,
                                    @RequestParam Long id) {
        try {
            checkAdminOrOwnerPerm(user, id);
            return fileService.read(albumService.getById(id).getUuid(), fileValidator);
        } catch (IOException e) {
            return new ResponseEntity<>("Cover not found", HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Album not found", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cover/")
    @PreAuthorize("hasAuthority('user:perm')")
    ResponseEntity<?> deleteAlbumCover(@AuthenticationPrincipal User user,
                                       @RequestParam Long id) {
        try {
            checkAdminOrOwnerPerm(user, id);
            fileService.delete(albumService.getById(id).getUuid(), fileValidator);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        } catch (DestroyFailedException e) {
            return new ResponseEntity<>("Album not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cover/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    ResponseEntity<?> uploadAlbumCover(@AuthenticationPrincipal User user,
                                       @RequestParam Long id, @RequestParam MultipartFile file) {
        try{
            checkAdminOrOwnerPerm(user, id);
            String name = fileService.uploadFile(file, fileValidator, UUID.randomUUID().toString());
            return new ResponseEntity<>(albumService.setCover(id, name), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>("Album not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cover/update/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    ResponseEntity<?> updateAlbumCover(@AuthenticationPrincipal User user,
                                       @RequestParam Long id, @RequestParam MultipartFile file) {
        ResponseEntity<?> res = deleteAlbumCover(user, id);
        if (!res.getStatusCode().equals(HttpStatus.OK))
            return res;
        return uploadAlbumCover(user, id, file);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
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

    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority('admin:perm', 'owner:perm')")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,
                                    @RequestParam Long id) {
        try{
            checkAdminOrOwnerPerm(user, id);
            albumService.delete(id);
            return deleteAlbumCover(user, id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        }
    }

}
