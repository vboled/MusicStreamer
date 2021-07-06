package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.User;

import java.util.List;

public interface UserService {

    void create(User user);

    List<User> readAll();

    User read(String userName);

    User read(int id);

    boolean update(User user, int id);

    boolean delete(int id);
}
