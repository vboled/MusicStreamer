package vboled.netcracker.musicstreamer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.user.Role;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.repository.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final String USER_PATTERN = "^[a-zA-z]+(\\w{0,30})$";

    private final String EMAIL_PATTERN =
            "^([A-Za-z\\d])([\\w-.]{0,20})([A-Za-z\\d])@([A-Za-z\\d]([A-Za-z\\d-])*[A-Za-z\\d].)*" +
            "([A-Za-z\\d]([A-Za-z\\d-])*[A-Za-z\\d])" +
            "(.[A-Za-z]+)$";

    private final String PHONE_PATTERN = "^(\\d{1,3})(\\d{10})$";

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        checkEmail(user.getEmail());
        checkPhone(user.getPhoneNumber());
        checkUserName(user.getUserName());
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
            checkEmail(user.getEmail());
            checkPhone(user.getPhoneNumber());
            checkUserName(user.getUserName());
            user.setId(id);
            user.setCreateDate(userRepository.getById(id).getCreateDate());
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

    @Override
    public void checkEmail(String email) {
        if (!email.toLowerCase(Locale.ROOT).matches(EMAIL_PATTERN))
            throw new IllegalArgumentException("Wrong Email format!!!");
        if (userRepository.existsByEmail(email))
            throw new IllegalArgumentException("Email is already taken!!!");
    }

    @Override
    public void checkPhone(String phone) {
        if (!phone.matches(PHONE_PATTERN))
            throw new IllegalArgumentException("Wrong phone format!!!");
        if (userRepository.existsByPhoneNumber(phone))
            throw new IllegalArgumentException("Phone Number is already taken!!!");
    }

    @Override
    public void checkUserName(String userName) {
        if (!userName.matches(USER_PATTERN))
            throw new IllegalArgumentException("Wrong username format!!!");
        if (userRepository.existsByUserName(userName))
            throw new IllegalArgumentException("Username is already taken!!!");
    }

    @Override
    public boolean updateEmail(String email, int id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setEmail(email);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePhone(String phone, int id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setPhoneNumber(phone);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(String password, int id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidUserName(String userName) {
        return userName.matches(USER_PATTERN);
    }

    @Override
    public boolean isValidEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    @Override
    public boolean isValidPhoneNumber(String phone) {
        return phone.matches(PHONE_PATTERN);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.findByUserName(userName).get();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).get();
    }

    @Override
    public User updateCommonFields(User update, int id) {
        if (!userRepository.existsById(id))
            throw new UsernameNotFoundException("No user with such id");
        User userToUpdate = userRepository.getById(id);
        if (update.getName() != null)
            userToUpdate.setName(update.getName());
        if (update.getLastName() != null)
            userToUpdate.setLastName(update.getLastName());
        userRepository.save(userToUpdate);
        return userToUpdate;
    }
}
