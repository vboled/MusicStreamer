package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;
import vboled.netcracker.musicstreamer.dto.AuthDTO;
import vboled.netcracker.musicstreamer.service.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ApplicationConfiguration.SecurityConfiguration securityConfiguration;

    public AuthController(AuthService authService, ApplicationConfiguration configuration) {
        this.securityConfiguration = configuration.getSecurityConfiguration();
        this.authService = authService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getToken(@RequestParam String login, @RequestParam String password,
                                      HttpServletResponse response) {
        String cookieName = securityConfiguration.getJwtConfiguration().getHeader();
        try {
            Cookie cookie = new Cookie(
                    cookieName,
                    authService.createJwtToken(authService.validateCredentials(login, password))
                            .getAccessToken()
            );
            cookie.setPath(securityConfiguration.getJwtConfiguration().getPath());
            cookie.setMaxAge(securityConfiguration.getJwtConfiguration().getExpireTimeSeconds().intValue());
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new ResponseEntity<>("Ok!", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Not Ok!", HttpStatus.FORBIDDEN);
        }
    }
}
