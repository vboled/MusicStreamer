package vboled.netcracker.musicstreamer.controllers;

import io.swagger.annotations.ResponseHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.dto.AuthDto;
import vboled.netcracker.musicstreamer.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/")
    public ResponseEntity<?> getCookie(@RequestBody AuthDto authDto) {
        try {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.SET_COOKIE, authService.createCookie(authService.validateCredentials(authDto.getLogin(), authDto.getPassword())));
            return new ResponseEntity<>("Ok!", responseHeaders, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Not Ok!", HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
