package vboled.netcracker.musicstreamer.service;

import javax.servlet.http.Cookie;
import vboled.netcracker.musicstreamer.model.user.User;

public interface AuthService {

    String createCookie(User user);

    User validateCredentials(String login, String password);

}
