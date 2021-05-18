package kr.ac.jejunu.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {
    static UserDao userDao;

    @BeforeAll
    public static void setup() throws ClassNotFoundException {
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        StaticApplicationContext applicationContext = new StaticApplicationContext();

        BeanDefinition dataSourceBeanDefinition = new RootBeanDefinition(SimpleDriverDataSource.class);
        dataSourceBeanDefinition.getPropertyValues().addPropertyValue("driverClass", Class.forName(System.getenv("DB_CLASSNAME")));
        dataSourceBeanDefinition.getPropertyValues().addPropertyValue("url", System.getenv("DB_URL"));
        dataSourceBeanDefinition.getPropertyValues().addPropertyValue("username", System.getenv("DB_USERNAME"));
        dataSourceBeanDefinition.getPropertyValues().addPropertyValue("password", System.getenv("DB_PASSWORD"));
        applicationContext.registerBeanDefinition("dataSource", dataSourceBeanDefinition);

        BeanDefinition jdbcContextBeanDefinition = new RootBeanDefinition(JdbcTemplate.class);
        jdbcContextBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference("dataSource"));
        applicationContext.registerBeanDefinition("jdbcContext", jdbcContextBeanDefinition);

        BeanDefinition beanDefinition = new RootBeanDefinition(UserDao.class);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference("jdbcContext"));
        applicationContext.registerBeanDefinition("userDao", beanDefinition);

        userDao = applicationContext.getBean("userDao", UserDao.class);
    }


    @Test
    public void get() throws SQLException, ClassNotFoundException {
        Integer id = 1;
        String name = "hyun";
        String password = "1234";

        User user = userDao.findById(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void insert() throws SQLException {
        String name = "hyun";
        String password = "1234";

        User user = new User();
        user.setName(name);
        user.setPassword(password);
//        DaoFactory daoFactory = new DaoFactory();
//        UserDao userDao = daoFactory.getUserDao();
        userDao.insert(user);

        User insertedUser = userDao.findById(user.getId());

        assertThat(user.getId(), greaterThan(0));
        assertThat(insertedUser.getId(), is(user.getId()));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public void update() throws SQLException {
        String name = "재현";
        String password = "1111";

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);

        user.setName("hyun");
        user.setPassword("1234");

        userDao.update(user);

        User updatedUser = userDao.findById(user.getId());

        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getName(), is(user.getName()));
        assertThat(updatedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public void delete() throws SQLException {
        String name = "hyun";
        String password = "1234";

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);

        userDao.delete(user.getId());

        User deletedUser = userDao.findById(user.getId());

        assertThat(deletedUser, nullValue());
    }
}
