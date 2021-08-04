package vboled.netcracker.musicstreamer.service;

import vboled.netcracker.musicstreamer.model.user.User;

import java.util.List;
import java.util.NoSuchElementException;

public interface UserService {

    User create(User user);

    List<User> readAll();

    User read(String userName);

    User read(Long id) throws NoSuchElementException;

    boolean update(User user, Long id);

    boolean delete(Long id);

    boolean updatePassword(String newPassword, Long id);

    void checkEmail(String email);

    void checkPhone(String phone);

    void checkUserName(String userName);

    boolean updateEmail(String email, Long id);

    boolean updatePhone(String phone, Long id);

    boolean isValidUserName(String userName);

    boolean isValidEmail(String email);

    boolean isValidPhoneNumber(String phone);

    User getByUserName(String userName);

    User getByEmail(String email);

    User getByPhoneNumber(String phoneNumber);

    User userFullUpdate(User update);

    User userNotFullUpdate(User update, Long id);

    boolean matches(String hashed, String common);

    String encode(String toHash);
}
