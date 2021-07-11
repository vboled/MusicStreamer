package vboled.netcracker.musicstreamer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Role;
import vboled.netcracker.musicstreamer.model.User;
import vboled.netcracker.musicstreamer.repository.UserRepository;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
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
    public User read(int id) {
        return userRepository.getById(id);
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
