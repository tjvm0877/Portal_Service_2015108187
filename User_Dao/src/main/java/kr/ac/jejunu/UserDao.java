package kr.ac.jejunu;

import java.sql.*;

public class UserDao {
    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User findById(Integer id) throws SQLException {
        //인터페이스를 바로 구현 => 템플릿 콜백패턴, 인터페이스 자체가 하나의 템플릿이 되고 거기서 시행되어지는 메소드자체가 콜백메소드라고 이름을 붙인다.
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from  userinfo where id = ?"
            );
            preparedStatement.setInt(1, id);
            return preparedStatement;
        };
        User user = jdbcContext.jdbcContextForFindById(statementStrategy);
        return user;
    }

    public void insert(User user) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into userinfo (name, password) values ( ?, ? )"
                    , Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            return preparedStatement;
        };
        jdbcContext.jdbcContextForInsert(user, statementStrategy);
    }

    public void update(User user) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection .prepareStatement(
                    "update userinfo set name = ?, password = ? where id = ?"
            );
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getId());
            return preparedStatement;
        };
        jdbcContext.jdbcContextForUpdate(statementStrategy);
    }

    public void delete(Integer id) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from userinfo where id = ?"
            );
            preparedStatement.setInt(1, id);
            return preparedStatement;
        };
        jdbcContext.jdbcContextForUpdate(statementStrategy);
    }
}
