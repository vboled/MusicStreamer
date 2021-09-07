package vboled.netcracker.musicstreamer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    static final String LOGIN_FORM_URL = "/login";
    static final String TARGET_AFTER_SUCCESSFUL_LOGIN_PARAM = "target";

    private final CookieSecurityContextRepository cookieSecurityContextRepository;
    private final LoginWithTargetUrlAuthenticationEntryPoint loginWithTargetUrlAuthenticationEntryPoint;
    private final RedirectToOriginalUrlAuthenticationSuccessHandler redirectToOriginalUrlAuthenticationSuccessHandler;
    private final UserDetailsService userDetailsService;

    protected WebSecurityConfig(CookieSecurityContextRepository cookieSecurityContextRepository,
                                LoginWithTargetUrlAuthenticationEntryPoint loginWithTargetUrlAuthenticationEntryPoint, RedirectToOriginalUrlAuthenticationSuccessHandler redirectToOriginalUrlAuthenticationSuccessHandler,
                                @Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService) {
        super();
        this.cookieSecurityContextRepository = cookieSecurityContextRepository;
        this.loginWithTargetUrlAuthenticationEntryPoint = loginWithTargetUrlAuthenticationEntryPoint;
        this.redirectToOriginalUrlAuthenticationSuccessHandler = redirectToOriginalUrlAuthenticationSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()

                // store SecurityContext in Cookie / delete Cookie on logout
                .securityContext().securityContextRepository(cookieSecurityContextRepository)
                .and().logout().permitAll().deleteCookies(SignedUserCookie.NAME)

                // deactivate RequestCache and append originally requested URL as query parameter to login form request
                .and().requestCache().disable()
                .exceptionHandling().authenticationEntryPoint(loginWithTargetUrlAuthenticationEntryPoint)

                // configure form-based login
                .and().formLogin()
                .loginPage(LOGIN_FORM_URL)
                // after successful login forward user to originally requested URL
                .successHandler(redirectToOriginalUrlAuthenticationSuccessHandler)

                .and().authorizeRequests()
//                .antMatchers("/api/v1/whoami").permitAll()
                .antMatchers("/api/v1/create-user").permitAll()
                .antMatchers(LOGIN_FORM_URL).permitAll()
                .antMatchers("/**").authenticated();

//                .antMatchers("/").permitAll()
//                .antMatchers("/api/v1/whoami").permitAll()
//                .antMatchers("/api/v1/auth/").permitAll()
//                .antMatchers("/api/v1").authenticated();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/other").setViewName("other");
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

}
