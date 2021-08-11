package vboled.netcracker.musicstreamer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import vboled.netcracker.musicstreamer.model.user.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CookieSecurityContextRepository implements SecurityContextRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CookieSecurityContextRepository.class);
    private static final String EMPTY_CREDENTIALS = "";
    private static final String ANONYMOUS_USER = "anonymousUser";

    private final String cookieHmacKey;

    public CookieSecurityContextRepository(@Value("${auth.cookie.hmac-key}") String cookieHmacKey) {
        this.cookieHmacKey = cookieHmacKey;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        HttpServletResponse response = requestResponseHolder.getResponse();
        requestResponseHolder.setResponse(new SaveToCookieResponseWrapper(request, response));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        readUserFromCookie(request).ifPresent(user ->
                context.setAuthentication(new UsernamePasswordAuthenticationToken(user, EMPTY_CREDENTIALS, user.getAuthorities())));

        return context;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        SaveToCookieResponseWrapper responseWrapper = (SaveToCookieResponseWrapper) response;
        if (!responseWrapper.isContextSaved()) {
            responseWrapper.saveContext(context);
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return readUserFromCookie(request).isPresent();
    }

    private Optional<User> readUserFromCookie(HttpServletRequest request) {
        return readCookieFromRequest(request)
                .map(this::createUser);
    }

    private Optional<Cookie> readCookieFromRequest(HttpServletRequest request) {
        if (request.getCookies() == null) {
            LOG.debug("No cookies in request");
            return Optional.empty();
        }

        Optional<Cookie> maybeCookie = Stream.of(request.getCookies())
                .filter(c -> SignedUserCookie.NAME.equals(c.getName()))
                .findFirst();

        if (maybeCookie.isEmpty()) {
            LOG.debug("No {} cookie in request", SignedUserCookie.NAME);
        }

        return maybeCookie;
    }

    private User createUser(Cookie cookie) {
        return new SignedUserCookie(cookie, cookieHmacKey).getUser();
    }

    private class SaveToCookieResponseWrapper extends SaveContextOnUpdateOrErrorResponseWrapper {
        private final Logger LOG = LoggerFactory.getLogger(SaveToCookieResponseWrapper.class);
        private final HttpServletRequest request;

        SaveToCookieResponseWrapper(HttpServletRequest request, HttpServletResponse response) {
            super(response, true);
            this.request = request;
        }

        @Override
        protected void saveContext(SecurityContext securityContext) {
            HttpServletResponse response = (HttpServletResponse) getResponse();
            Authentication authentication = securityContext.getAuthentication();
            if (authentication == null) {
                LOG.debug("No securityContext.authentication, skip saveContext");
                return;
            }

            if (ANONYMOUS_USER.equals(authentication.getPrincipal())) {
                LOG.debug("Anonymous User SecurityContext, skip saveContext");
                return;
            }

            if (!(authentication.getPrincipal() instanceof User)) {
                LOG.warn("securityContext.authentication.principal of unexpected type {}, skip saveContext", authentication.getPrincipal().getClass().getCanonicalName());
                return;
            }

            User user = (User) authentication.getPrincipal();
            SignedUserCookie cookie = new SignedUserCookie(user, cookieHmacKey);
            cookie.setSecure(request.isSecure());
            response.addCookie(cookie);
            LOG.debug("SecurityContext for principal '{}' saved in Cookie", user.getUsername());
        }
    }

}
