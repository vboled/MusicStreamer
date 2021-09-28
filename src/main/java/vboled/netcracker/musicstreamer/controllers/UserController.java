package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.user.Permission;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.model.user.UserView;
import vboled.netcracker.musicstreamer.service.AlbumService;
import vboled.netcracker.musicstreamer.service.ArtistService;
import vboled.netcracker.musicstreamer.service.UserService;
import vboled.netcracker.musicstreamer.view.ContentView;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AlbumService albumService;
    private final ArtistService artistService;

    @Autowired
    public UserController(UserService userService, AlbumService albumService, ArtistService artistService) {
        this.userService = userService;
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping("/playlists/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> getAllPlaylists(@AuthenticationPrincipal User user) {
        try {
            return new ResponseEntity<>(userService.getAllPlaylists(user.getId()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/info/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> read(@AuthenticationPrincipal User user) {
        try {
            return new ResponseEntity<>(userService.read(user.getId()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/email/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> updateEmail(@AuthenticationPrincipal User user,
                                         @RequestBody Map<String, String> json) {
        String password = json.get("password"), newEmail = json.get("newEmail");
        if (password == null || newEmail == null)
            return new ResponseEntity<>("Wrong parameters", HttpStatus.BAD_REQUEST);
        if(!userService.matches(password, user.getPassword()))
            return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
        try {
            userService.checkEmail(newEmail);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        final boolean updated = userService.updateEmail(newEmail, user.getId());

        return updated
                ? new ResponseEntity<>(newEmail, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update/phone/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> updatePhoneNumber(@AuthenticationPrincipal User user,
                                               @RequestBody Map<String, String> json) {
        String password = json.get("password"), newPhoneNumber = json.get("newPhoneNumber");
        if (password == null || newPhoneNumber == null)
            return new ResponseEntity<>("Wrong parameters", HttpStatus.BAD_REQUEST);
        if(!userService.matches(password, user.getPassword()))
            return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
        try {
            userService.checkPhone(newPhoneNumber);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        final boolean updated = userService.updatePhone(newPhoneNumber, user.getId());
        return updated
                ? new ResponseEntity<>(newPhoneNumber, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update/password/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal User user,
                                            @RequestBody Map<String, String> json) {
        String password = json.get("password"), newPassword1 = json.get("newPassword1"),
                newPassword2 = json.get("newPassword2");
        if (password == null || newPassword2 == null || newPassword1 == null)
            return new ResponseEntity<>("Wrong parameters", HttpStatus.BAD_REQUEST);
        if(!userService.matches(password, user.getPassword()))
            return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
        if (!newPassword1.equals(newPassword2))
            return new ResponseEntity<>("Input password's doesn't match", HttpStatus.BAD_REQUEST);
        final boolean updated = userService.updatePassword(userService.encode(newPassword1), user.getId());
        return updated
                ? new ResponseEntity<>("Password was changed!!!", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user,
                                        @RequestBody User updateUser) {
        try{
            User res = null;
            if (user.getRole().getPermissions().contains(Permission.ADMIN_PERMISSION)) {
                res = userService.userFullUpdate(updateUser);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            else {
                res = userService.userNotFullUpdate(updateUser, user.getId());
                return new ResponseEntity<>(new UserView(res), HttpStatus.OK);
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/content/")
    @PreAuthorize("hasAnyAuthority('owner:perm', 'admin:perm')")
    public ResponseEntity<?> getContentByUser(@AuthenticationPrincipal User user) {
        try {
            User res = userService.getByUserName(user.getUserName());
            Set<Permission> perm = user.getRole().getPermissions();
            if (!(perm.contains(Permission.ADMIN_PERMISSION) ||
                    (perm.contains(Permission.OWNER_PERMISSION)))) {
                throw new IllegalAccessError();
            }
            return new ResponseEntity<>(new ContentView(res, albumService.getAlbumsByOwnerId(res.getId()),
                    artistService.getArtistsByOwnerId(res.getId())), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("You don't have permission", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recommendations/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> getRecommendations(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getRecommendations(user), HttpStatus.OK);
    }

    @GetMapping("/recommendations-fresh/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> getFreshRecommendations(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getRefreshedRecommendations(user), HttpStatus.OK);
    }

}
