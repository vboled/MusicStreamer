package vboled.netcracker.musicstreamer.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.UserService;

import java.util.*;

@Component
public class CookieDecoder {
    private final UserService userService;
    private final ApplicationConfiguration.SecurityConfiguration.JwtConfiguration jwtConfiguration;

    @Autowired
    public CookieDecoder(UserService userService,
                         ApplicationConfiguration configuration) {
        this.userService = userService;
        this.jwtConfiguration = configuration.getSecurityConfiguration().getJwtConfiguration();
    }

    public UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token) {
        if (StringUtils.hasLength(token)) {
            byte[] signingKey = jwtConfiguration.getSecret().getBytes();

            Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token);

            String username = parsedToken
                    .getBody()
                    .getSubject();

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            ((ArrayList) parsedToken.getBody().get("role")).forEach(
                     e -> authorities.add(new SimpleGrantedAuthority(((Map) e).get("authority").toString()))
            );

            User user = userService.getByUserName(username);

            if (StringUtils.hasLength(username)) {
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        }
        return null;
    }
}
