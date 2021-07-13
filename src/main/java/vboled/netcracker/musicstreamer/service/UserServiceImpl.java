package vboled.netcracker.musicstreamer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.user.Role;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.repository.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final String USER_PATTERN = "[a-zA-z]+(\\w{0,30})";

    private final String EMAIL_PATTERN =
            "([A-Za-z\\d])([\\w-.]{0,20})([A-Za-z\\d])@([A-Za-z\\d]([A-Za-z\\d-])*[A-Za-z\\d].)*" +
            "([A-Za-z\\d]([A-Za-z\\d-])*[A-Za-z\\d])" +
            "((.ru)|(.com)|(.net)|(.org))";

    private final String PHONE_PATTERN = "\\+(\\d{1,3})(\\d{10})";

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        if (!user.getUserName().matches(USER_PATTERN))
            throw new IllegalArgumentException("Wrong username format!!!");
        if (userRepository.existsByUserName(user.getUserName()))
            throw new IllegalArgumentException("Username is already taken!!!");
        if (!user.getEmail().toLowerCase(Locale.ROOT).matches(EMAIL_PATTERN))
            throw new IllegalArgumentException("Wrong Email format!!!");
        if (userRepository.existsByEmail(user.getEmail()))
            throw new IllegalArgumentException("Email is already taken!!!");
        if (!user.getPhoneNumber().matches(PHONE_PATTERN))
            throw new IllegalArgumentException("Wrong phone format!!!");
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber()))
            throw new IllegalArgumentException("Phone Number is already taken!!!");
        userRepository.save(user);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User read(String userName) {
        return userRepository.findByUserName(userName).get();
    }

    @Override
    public User read(int id) throws NoSuchElementException {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean update(User user, int id) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateName(String newName, int id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setName(newName);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateLastName(String newName, int id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setName(newName);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRole(int id, Role role) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setRole(role);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
