package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.model.user.UserView;
import vboled.netcracker.musicstreamer.security.SecurityUser;
import vboled.netcracker.musicstreamer.service.UserService;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/info/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> read(@AuthenticationPrincipal SecurityUser user) {
        try {
            return new ResponseEntity<>(new UserView(userService.read(user.getId())), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/email/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> updateEmail(@AuthenticationPrincipal SecurityUser user,
                                         @RequestBody Map<String, String> json) {
        String password = json.get("password"), newEmail = json.get("newEmail");
        if (password == null || newEmail == null)
            return new ResponseEntity<>("Wrong parameters", HttpStatus.BAD_REQUEST);
        if(!passwordEncoder.matches(password, user.getPassword()))
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
    public ResponseEntity<?> updatePhoneNumber(@AuthenticationPrincipal SecurityUser user,
                                               @RequestBody Map<String, String> json) {
        String password = json.get("password"), newPhoneNumber = json.get("newPhoneNumber");
        if (password == null || newPhoneNumber == null)
            return new ResponseEntity<>("Wrong parameters", HttpStatus.BAD_REQUEST);
        if(!passwordEncoder.matches(password, user.getPassword()))
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
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal SecurityUser user,
                                            @RequestBody Map<String, String> json) {
        String password = json.get("password"), newPassword1 = json.get("newPassword1"),
                newPassword2 = json.get("newPassword2");
        if (password == null || newPassword2 == null || newPassword1 == null)
            return new ResponseEntity<>("Wrong parameters", HttpStatus.BAD_REQUEST);
        if(!passwordEncoder.matches(password, user.getPassword()))
            return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
        if (!newPassword1.equals(newPassword2))
            return new ResponseEntity<>("Input password's doesn't match", HttpStatus.BAD_REQUEST);
        final boolean updated = userService.updatePassword(passwordEncoder.encode(newPassword1), user.getId());
        return updated
                ? new ResponseEntity<>("Password was changed!!!", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update/")
    @PreAuthorize("hasAuthority('user:perm')")
    public ResponseEntity<?> updateCommonField(@AuthenticationPrincipal SecurityUser user,
                                        @RequestBody User updateUser) {
        try{
            User res = userService.updateCommonFields(updateUser, user.getId());
            return new ResponseEntity<>(new UserView(res), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
