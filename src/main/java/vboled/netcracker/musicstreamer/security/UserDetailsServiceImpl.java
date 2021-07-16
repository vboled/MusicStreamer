package vboled.netcracker.musicstreamer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.service.UserService;

@Service("userDetailServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = null;
        if (userService.isValidUserName(userName)) {
            user = userService.getByUserName(userName);
        } else if (userService.isValidPhoneNumber(userName)) {
            user = userService.getByPhoneNumber(userName);
        } else if (userService.isValidEmail(userName)) {
            user = userService.getByEmail(userName);
        }
        return SecurityUser.fromUser(user);
    }
}
