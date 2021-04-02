package kr.ac.jejunu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {
    static UserDao userDao; // jvm의 메모리 구조, 메모리 영역이 어떻게 관리되는지? 무엇이 어느 영역에서 사용되는지?

    @BeforeAll
    public  static void  setup(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);

        userDao = applicationContext.getBean("userDao", UserDao.class); // Dependency LookUP
    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        Integer id = 1;
        String name = "재현";
        String password = "1234";
//        DaoFactory daoFactory = new DaoFactory();
//        UserDao userDao = daoFactory.getUserDao();

        User user = userDao.findById(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void insert() throws SQLException, ClassNotFoundException {
        String name = "Hyun";
        String password = "1234";
        User user = new User();
        user.setName(name);
        user.setPassword(password);

        userDao.insert(user);

        User insertedUser = userDao.findById(user.getId());

        assertThat(user.getId(), greaterThan(0));
        assertThat(user.getId(), is (user.getId()));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public  void  update() throws SQLException {

        String name = "Hyun";
        String password = "1234";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);

        user.setName("kim");
        user.setPassword("1212");

        userDao.update(user);

        User udatedUser = userDao.findById(user.getId());

        assertThat(udatedUser.getId(), is(user.getId()));
        assertThat(udatedUser.getName(), is(user.getName()));
        assertThat(udatedUser.getPassword(), is(user.getPassword()));
    }

    public  void  delete() throws SQLException {
        String name = "Hyun";
        String password = "1234";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);

        userDao.delete(user.getId());

        User deletedUser = userDao.findById(user.getId());

        assertThat(deletedUser, nullValue());
    }
//
//    @Test
//    public void getHalla() throws SQLException, ClassNotFoundException {
//        Integer id = 1;
//        String name = "재현";
//        String password = "1234";
//
//        UserDao userDao = new UserDao(new HanlaConnectionMaker());
//        User user = userDao.findById(id);
//        assertThat(user.getId(), is(id));
//        assertThat(user.getName(), is(name));
//        assertThat(user.getPassword(), is(password));
//    }
//
//    @Test
//    public void insertHalla() throws SQLException, ClassNotFoundException {
//        String name = "재현";
//        String password = "1111";
//
//        User user = new User();
//        user.setName(name);
//        user.setPassword(password);
//        UserDao userDao = new UserDao(new HanlaConnectionMaker());
//        userDao.insert(user);
//
//        User insertedUser = userDao.findById(user.getId());
//
//        assertThat(user.getId(), greaterThan(0));
//        assertThat(insertedUser.getId(), is(user.getId()));
//        assertThat(insertedUser.getName(), is(user.getName()));
//        assertThat(insertedUser.getPassword(), is(user.getPassword()));
//    }
}

