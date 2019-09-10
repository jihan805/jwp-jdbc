package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.ResultMapper;
import next.model.User;

import java.util.List;

public class UserDao {
    private JdbcTemplate template;
    private ResultMapper<User> resultMapper;

    public UserDao() {
        template = new JdbcTemplate();
        resultMapper = new ResultMapper<>(User.class);
    }

    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        template.executeQuery(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? where userId = ?";
        template.executeQuery(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List findAll() {
        String sql = "SELECT userId, password, name, email FROM USERS";

        return template.executeQueryForList(sql, resultMapper);
    }

    public User findByUserId(String userId) {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";

        return template.executeQuery(sql, resultMapper, userId);
    }
}
