package vboled.netcracker.musicstreamer.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final String username;

    private final int id;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;

    public SecurityUser(String username, int id, String password, List<SimpleGrantedAuthority> authorities) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.authorities = authorities;
    }

    public int getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetails fromUser(User user) {
        return new SecurityUser(user.getUserName(), user.getId(), user.getPassword(),
                new ArrayList<>(user.getRole().getAuthorities())
        );
    }
}
