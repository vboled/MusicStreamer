package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.User;
import vboled.netcracker.musicstreamer.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<List<User>> read() {
        final List<User> users = userService.readAll();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<User> readByUserName(@PathVariable(name = "username") String userName) {
        final User user = userService.read(userName);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasAuthority('admin:perm')" + " or authentication.principal.getId() == #id ")
    public ResponseEntity<User> read(@PathVariable(name = "id") int id) {
        final User user = userService.read(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody User user) {
        final boolean updated = userService.update(user, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/name/{id}")
    @PreAuthorize("hasAuthority('admin:perm')"  + " or authentication.principal.getId() == #id ")
    public ResponseEntity<?> updateName(@PathVariable(name = "id") int id, @RequestBody String newName) {
        final boolean updated = userService.updateName(newName, id);

        return updated
                ? new ResponseEntity<>(newName, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/lastname/{id}")
    @PreAuthorize("hasAuthority('admin:perm')"  + " or authentication.principal.getId() == #id ")
    public ResponseEntity<?> updateLastName(@PathVariable(name = "id") int id, @RequestBody String newName) {
        final boolean updated = userService.updateLastName(newName, id);

        return updated
                ? new ResponseEntity<>(newName, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
