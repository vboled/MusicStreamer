package vboled.netcracker.musicstreamer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import vboled.netcracker.musicstreamer.model.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RedirectToOriginalUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RedirectToOriginalUrlAuthenticationSuccessHandler.class);
    private static final String DEFAULT_TARGET_URL = "/";


    public RedirectToOriginalUrlAuthenticationSuccessHandler() {
        super(DEFAULT_TARGET_URL);
        this.setTargetUrlParameter(WebSecurityConfig.TARGET_AFTER_SUCCESSFUL_LOGIN_PARAM);
    }


    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var targetUrl = super.determineTargetUrl(request, response, authentication);
        if (UrlUtils.isAbsoluteUrl(targetUrl)) {
            LOG.warn("Absolute target URL {} identified and suppressed", targetUrl);
            return DEFAULT_TARGET_URL;
        }
        return targetUrl;
    }
}
