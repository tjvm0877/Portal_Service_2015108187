package kr.ac.jejunu.user;

import java.sql.*;

public class UserDao {
    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User findById(Integer id) throws SQLException {
        Object[] prams = new Object[] {id};
        String sql = "select * from  userinfo where id = ?";
        return jdbcContext.findById(sql, prams);
    }

    public void insert(User user) throws SQLException {
        Object[] prams = new Object[] {user.getName(), user.getPassword()};
        String sql = "insert into userinfo (name, password) values ( ?, ? )";
        jdbcContext.insert(user, prams, sql);
    }

    public void update(User user) throws SQLException {
        Object[] prams = new Object[] {user.getName(), user.getPassword(), user.getId()};
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        jdbcContext.update(sql, prams);
    }

    public void delete(Integer id) throws SQLException {
        Object[] prams = new Object[] {id};
        String sql = "delete from userinfo where id = ?";
        jdbcContext.update(sql, prams);
    }
}
