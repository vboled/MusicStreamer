package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> create(@RequestBody User user) {
        try {
            userService.create(user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> read() {
        final List<User> users = userService.readAll();
        if (users == null || users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/username/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> readByUserName(@RequestBody String userName) {
        try {
            return new ResponseEntity<>(userService.read(userName), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> read(@RequestBody Long id) {
        try {
             return new ResponseEntity<>(userService.read(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> update(@RequestParam Long id, @RequestBody User user) {
        boolean updated;
        try {
            updated = userService.update(user, id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return updated
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>("User not found", HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/delete/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
