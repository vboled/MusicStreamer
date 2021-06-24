package vboled.netcrecker.musicstreamer.dao;

import org.springframework.stereotype.Component;
import vboled.netcrecker.musicstreamer.models.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {
    private int USERS_COUNT = 1;
    private List<User> users;

    {
        users = new ArrayList<>();
        users.add(new User(USERS_COUNT++, "Mike"));
        users.add(new User(USERS_COUNT++, "Ker"));
        users.add(new User(USERS_COUNT++, "Lok"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(int id) {
        return users.stream().filter(user->user.getId() == id).findAny().orElse(null);
    }

}
