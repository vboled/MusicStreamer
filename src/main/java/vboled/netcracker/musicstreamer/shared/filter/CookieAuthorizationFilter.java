package vboled.netcracker.musicstreamer.shared.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import vboled.netcracker.musicstreamer.config.ApplicationConfiguration;
import vboled.netcracker.musicstreamer.util.CookieDecoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class CookieAuthorizationFilter extends BasicAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final CookieDecoder cookieDecoder;
    private final ApplicationConfiguration.SecurityConfiguration.JwtConfiguration jwtConfiguration;

    public CookieAuthorizationFilter(AuthenticationManager authenticationManager,
                                     CookieDecoder cookieDecoder,
                                     ObjectMapper objectMapper,
                                     ApplicationConfiguration.SecurityConfiguration.JwtConfiguration jwtConfiguration) {
        super(authenticationManager);
        this.cookieDecoder = cookieDecoder;
        this.objectMapper = objectMapper;
        this.jwtConfiguration = jwtConfiguration;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().equals("/api/v1/auth/") ||
                httpServletRequest.getRequestURI().equals("/api/v1/create/") ||
                httpServletRequest.getRequestURI().equals("/api/v1/region/all/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException, ExpiredJwtException {
        UsernamePasswordAuthenticationToken authentication;
        try {
            authentication = getAuthentication(request);
        } catch (JwtException | UsernameNotFoundException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
            return;
        }
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
            throws JwtException, UsernameNotFoundException {
        if (request.getCookies() == null) {
            throw new JwtException("no cookies");
        }
        Cookie authCookie = Arrays
                .stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(jwtConfiguration.getHeader()))
                .findFirst()
                .orElseThrow(() -> new JwtException("no auth cookies"));
        return cookieDecoder.getAuthenticationFromToken(authCookie.getValue());
    }
}
