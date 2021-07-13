package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.user.UserAdminView;
import vboled.netcracker.musicstreamer.service.UserService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("authentication.principal.getId() == #id")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        try {
            return new ResponseEntity<>(new UserAdminView(userService.read(id)), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/name/{id}")
    @PreAuthorize("hasAuthority('admin:perm')"  + " or authentication.principal.getId() == #id ")
    public ResponseEntity<?> updateName(@PathVariable(name = "id") int id, @RequestBody String newName) {
        final boolean updated = userService.updateName(newName, id);

        return updated
                ? new ResponseEntity<>(newName, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update/lastname/{id}")
    @PreAuthorize("hasAuthority('admin:perm')"  + " or authentication.principal.getId() == #id ")
    public ResponseEntity<?> updateLastName(@PathVariable(name = "id") int id, @RequestBody String newName) {
        final boolean updated = userService.updateLastName(newName, id);

        return updated
                ? new ResponseEntity<>(newName, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
