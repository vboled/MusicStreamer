package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @GetMapping("/hello")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("SJF", HttpStatus.CREATED);
    }
}
