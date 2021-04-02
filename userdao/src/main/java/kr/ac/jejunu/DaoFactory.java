package kr.ac.jejunu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //실제 이 오브젝트는 스프링이 관리하는 오브젝트라고 선언
public class DaoFactory {
    @Bean
    public  UserDao userDao() {
        return  new UserDao(connectionMaker());
    }

    @Bean
    public  JejuConnectionMaker connectionMaker() {
        return  new JejuConnectionMaker();
    }
}
