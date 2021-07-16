package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.user.Role;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface UserService {

    void create(User user);

    List<User> readAll();

    User read(String userName);

    User read(int id) throws NoSuchElementException;

    boolean update(User user, int id);

    boolean delete(int id);

    boolean updateName(String newName, int id);

    boolean updateLastName(String newName, int id);

    boolean updatePassword(String newPassword, int id);

    boolean updateRole(int id, Role role);

    void checkEmail(String email);

    void checkPhone(String phone);

    void checkUserName(String userName);

    boolean updateEmail(String email, int id);

    boolean updatePhone(String phone, int id);

    boolean isValidUserName(String userName);

    boolean isValidEmail(String email);

    boolean isValidPhoneNumber(String phone);

    User getByUserName(String userName);

    User getByEmail(String email);

    User getByPhoneNumber(String phoneNumber);

    User updateCommonFields(User update, int id);
}
