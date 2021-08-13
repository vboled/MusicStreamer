package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vboled.netcracker.musicstreamer.config.SignedUserCookie;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.AuthService;
import vboled.netcracker.musicstreamer.service.UserService;

@Component
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final String cookieHmacKey;


    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserService userService,
                           @Value("${auth.cookie.hmac-key}") String cookieHmacKey) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.cookieHmacKey = cookieHmacKey;
    }

    @Override
    public String createCookie(User user) {
        return new SignedUserCookie(userService, user, cookieHmacKey).getValue();
    }

    @Override
    public User validateCredentials(String login, String password) {
        User user = userService.getByUserName(login);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        return user;
    }
}
