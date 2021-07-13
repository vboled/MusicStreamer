package vboled.netcracker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.model.user.Role;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.model.user.UserAdminView;
import vboled.netcracker.musicstreamer.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/")
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
    public ResponseEntity<List<User>> read() {
        final List<User> users = userService.readAll();

        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> readByUserName(@PathVariable(name = "username") String userName) {
        try {
            return new ResponseEntity<>(new UserAdminView(userService.read(userName)), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        try {
             return new ResponseEntity<>(new UserAdminView(userService.read(id)), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody User user) {
        final boolean updated = userService.update(user, id);

        return updated
                ? new ResponseEntity<>(new UserAdminView(user), HttpStatus.OK)
                : new ResponseEntity<>("User not found", HttpStatus.NOT_MODIFIED);
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
