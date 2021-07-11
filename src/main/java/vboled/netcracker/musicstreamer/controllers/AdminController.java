package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.Role;
import vboled.netcracker.musicstreamer.model.User;
import vboled.netcracker.musicstreamer.service.UserService;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(user.toString(), HttpStatus.CREATED);
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
    public ResponseEntity<String> readByUserName(@PathVariable(name = "username") String userName) {
        final User user = userService.read(userName);

        return user != null
                ? new ResponseEntity<>(user.toString(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<String> read(@PathVariable(name = "id") int id) {
        final User user = userService.read(id);

        return user != null
                ? new ResponseEntity<>(user.toString(), HttpStatus.OK)
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

    @PutMapping("/admin/role/{id}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> setRole(@PathVariable(name = "id") int id, @RequestParam String roleName) {
        Role role;
        if (roleName.toLowerCase(Locale.ROOT).equals("admin"))
            role = Role.ADMIN;
        else if (roleName.toLowerCase(Locale.ROOT).equals("owner"))
            role = Role.OWNER;
        else if (roleName.toLowerCase(Locale.ROOT).equals("user"))
            role = Role.USER;
        else
            return new ResponseEntity<>("No such role", HttpStatus.BAD_REQUEST);
        final boolean updated = userService.updateRole(id, role);

        return updated
                ? new ResponseEntity<>(role.name(), HttpStatus.OK)
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
