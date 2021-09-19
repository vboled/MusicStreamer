package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.dto.AuthDTO;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.AuthService;
import vboled.netcracker.musicstreamer.service.UserService;
import vboled.netcracker.musicstreamer.util.CookieGenerator;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final CookieGenerator cookieGenerator;
    private final UserService userService;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder, CookieGenerator cookieGenerator, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.cookieGenerator = cookieGenerator;
        this.userService = userService;
    }

    @Override
    public User validateCredentials(String login, String password) {
        User user = userService.getByUserName(login);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        return user;
    }

    @Override
    public AuthDTO createJwtToken(User user) {
        return new AuthDTO().setAccessToken(cookieGenerator.create(user));
    }
}
