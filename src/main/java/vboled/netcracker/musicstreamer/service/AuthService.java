package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.user.User;

import vboled.netcracker.musicstreamer.dto.AuthDTO;
import vboled.netcracker.musicstreamer.model.user.User;

public interface AuthService {
    AuthDTO createJwtToken(User user);
    User validateCredentials(String login, String password);
}
