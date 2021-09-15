package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.Listening;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.ArtistService;
import vboled.netcracker.musicstreamer.service.ListeningService;
import vboled.netcracker.musicstreamer.service.UserService;

import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/listening")
public class ListeningController {

    private final ListeningService listeningService;
    private final UserService userService;
    private final ArtistService artistService;

    public ListeningController(ListeningService listeningService, UserService userService, ArtistService artistService) {
        this.listeningService = listeningService;
        this.userService = userService;
        this.artistService = artistService;
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
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> create(@RequestBody Listening listening) {
        try{
            return new ResponseEntity<>(listeningService.create(listening), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Song or User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> update(@RequestParam Long id, @RequestParam Long seconds) {
        try{
            return new ResponseEntity<>(listeningService.updateSeconds(id, seconds), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Listening not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-user/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> getListeningByUser(@RequestParam Long userID) {
        try{
            return new ResponseEntity<>(listeningService.getAllByUser(userService.read(userID)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-artist/")
    @PreAuthorize("hasAnyAuthority('owner:perm, admin:perm')")
    public ResponseEntity<?> getListeningByArtist(@AuthenticationPrincipal User user,
                                                   @RequestParam Long artistID) {
        try{
            checkAdminOrOwnerPerm(user, artistID);
            return new ResponseEntity<>(listeningService.getAllByArtist(artistID), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User or Artist not found", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(listeningService.getAll(), HttpStatus.OK);
    }

}
