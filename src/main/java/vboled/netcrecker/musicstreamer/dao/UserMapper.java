package vboled.netcrecker.musicstreamer.dao;

import org.springframework.jdbc.core.RowMapper;
import vboled.netcrecker.musicstreamer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("userName"));
        return user;
    }
}
