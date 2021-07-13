package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.user.Role;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.List;
import java.util.NoSuchElementException;

public interface UserService {

    void create(User user);

    List<User> readAll();

    User read(String userName);

    User read(int id) throws NoSuchElementException;

    boolean update(User user, int id);

    boolean delete(int id);

    boolean updateName(String newName, int id);

    boolean updateLastName(String newName, int id);

    boolean updateRole(int id, Role role);
}
