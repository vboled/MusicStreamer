package vboled.netcracker.musicstreamer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vboled.netcracker.musicstreamer.shared.filter.CorsFilter;
import vboled.netcracker.musicstreamer.shared.filter.CookieAuthorizationFilter;
import vboled.netcracker.musicstreamer.util.CookieDecoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final CookieDecoder cookieDecoder;
    private final ObjectMapper objectMapper;
    private final ApplicationConfiguration.SecurityConfiguration securityConfiguration;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService,
                          CookieDecoder cookieDecoder,
                          ObjectMapper objectMapper,
                          ApplicationConfiguration configuration) {
        this.userDetailsService = userDetailsService;
        this.cookieDecoder = cookieDecoder;
        this.objectMapper = objectMapper;
        this.securityConfiguration = configuration.getSecurityConfiguration();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/").permitAll()
                .antMatchers("/api/v1/whoami/").permitAll()
                .antMatchers("/api/v1/create/").permitAll()
                .antMatchers("/api/v1/region/all/").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new CorsFilter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CookieAuthorizationFilter(
                        authenticationManager(),
                        cookieDecoder,
                        objectMapper,
                        securityConfiguration.getJwtConfiguration()
                ), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
