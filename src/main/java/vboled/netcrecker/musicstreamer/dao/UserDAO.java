package vboled.netcrecker.musicstreamer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import vboled.netcrecker.musicstreamer.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {
    private int USERS_COUNT = 1;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM Users", new BeanPropertyRowMapper<>(User.class));
    }

    public User getUserById(int id) {
        return jdbcTemplate.query("SELECT * FROM Users WHERE id=?",
                new BeanPropertyRowMapper<>(User.class), new Object[]{id}).stream().findAny().orElse(null);
    }

    public void updateUser(int id, User user) {
        jdbcTemplate.update("UPDATE Users SET userName=?", user.getUserName());
    }

    public void addUser(User user) {
        jdbcTemplate.update("INSERT INTO Users VALUES(?, ?)", user.getId(), user.getUserName());
    }
}
