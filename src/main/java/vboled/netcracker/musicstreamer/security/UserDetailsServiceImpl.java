package vboled.netcracker.musicstreamer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.repository.UserRepository;

@Service("userDetailServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final String USER_PATTERN = "[a-zA-z]+(\\w{0,30})";

    private final String EMAIL_PATTERN =
            "([A-Za-z\\d])([\\w-.]{0,20})([A-Za-z\\d])@([A-Za-z\\d]([A-Za-z\\d-])*[A-Za-z\\d].)*" +
                    "([A-Za-z\\d]([A-Za-z\\d-])*[A-Za-z\\d])" +
                    "((.ru)|(.com)|(.net)|(.org))";

    private final String PHONE_PATTERN = "\\+(\\d{1,3})(\\d{10})";

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user;
        if (userName.matches(USER_PATTERN)) {
            user = userRepository.findByUserName(userName).orElseThrow(() ->
                    new UsernameNotFoundException("User with such username doesn't exist"));
        } else if (userName.matches(PHONE_PATTERN)) {
            user = userRepository.findByPhoneNumber(userName).orElseThrow(() ->
                    new UsernameNotFoundException("User with such phone doesn't exist"));
        } else if (userName.matches(EMAIL_PATTERN)) {
            user = userRepository.findByEmail(userName).orElseThrow(() ->
                    new UsernameNotFoundException("User with such email doesn't exist"));
        } else
            throw new IllegalArgumentException("Wrong format!!!");
        return SecurityUser.fromUser(user);
    }
}
