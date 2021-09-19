package vboled.netcracker.musicstreamer.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;
import vboled.netcracker.musicstreamer.model.user.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CookieGenerator {
    private final ApplicationConfiguration.SecurityConfiguration.JwtConfiguration jwtConfiguration;

    @Autowired
    public CookieGenerator(ApplicationConfiguration configuration) {
        this.jwtConfiguration = configuration.getSecurityConfiguration().getJwtConfiguration();
    }

    public String create(User user) throws NullPointerException {
        Map<String, Object> claims = new HashMap<>();

        claims.put("role", user.getRole().getAuthorities());
        claims.put("usr", user.getUsername());
        claims.put("id", user.getId());

        byte[] signingKey = jwtConfiguration.getSecret().getBytes();

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", jwtConfiguration.getType())
                .setIssuer(jwtConfiguration.getIssuer())
                .setAudience(jwtConfiguration.getAudience())
                .setSubject(user.getUsername())
                .setExpiration(
                        Date.from(LocalDateTime.now(ZoneId.of("UTC"))
                                .plusSeconds(jwtConfiguration.getExpireTimeSeconds())
                                .atZone(ZoneId.of("UTC"))
                                .toInstant()
                        )
                )
                .addClaims(claims)
                .compact();
    }
}
