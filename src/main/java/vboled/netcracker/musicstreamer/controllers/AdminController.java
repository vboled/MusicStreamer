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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/create/")
//    public ResponseEntity<?> create(@RequestBody User user) {
//        try {
//            userService.create(user);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//    }

    @GetMapping("/all/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> read() {
        final List<User> users = userService.readAll();
        if (users == null || users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<UserAdminView> res = new ArrayList<>(users.size());
        for (User user:users) {
            res.add(new UserAdminView(user));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/username/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> readByUserName(@RequestParam String userName) {
        try {
            return new ResponseEntity<>(new UserAdminView(userService.read(userName)), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> read(@RequestParam int id) {
        try {
             return new ResponseEntity<>(new UserAdminView(userService.read(id)), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('admin:perm')")
//    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody User user) {
//        final boolean updated = userService.update(user, id);
//
//        return updated
//                ? new ResponseEntity<>(new UserAdminView(user), HttpStatus.OK)
//                : new ResponseEntity<>("User not found", HttpStatus.NOT_MODIFIED);
//    }

    @PutMapping("/role/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> setRole(@RequestParam int id, @RequestParam String roleName) {
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

    @DeleteMapping("/delete/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> delete(@RequestParam int id) {
        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
