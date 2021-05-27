package kr.ac.jejunu.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public UserDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    public User findById(Integer id) throws SQLException {
        Object[] prams = new Object[] {id};
        String sql = "select * from  userinfo where id = ?";
        return jdbcTemplate.query(sql, prams, rs -> {
            User user = null;
            if(rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
            return user;
        });
    }

    public void insert(User user) throws SQLException {
        Object[] prams = new Object[] {user.getName(), user.getPassword()};
        String sql = "insert into userinfo (name, password) values ( ?, ? )";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
            );
            for(int i = 0; i< prams.length; i++) {
                preparedStatement.setObject(i + 1, prams[i]);
            }
            return preparedStatement;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
    }

    public void update(User user) throws SQLException {
        Object[] prams = new Object[] {user.getName(), user.getPassword(), user.getId()};
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        jdbcTemplate.update(sql, prams);
    }

    public void delete(Integer id) throws SQLException {
        Object[] prams = new Object[] {id};
        String sql = "delete from userinfo where id = ?";
        jdbcTemplate.update(sql, prams);
    }
}
