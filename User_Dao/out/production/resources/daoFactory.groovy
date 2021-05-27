import kr.ac.jejunu.user.UserDao
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SimpleDriverDataSource

beans {
    dataSource(SimpleDriverDataSource) {
        driverClass = System.getenv("DB_CLASSNAME")
        username = System.getenv("DB_USERNAME")
        password = System.getenv("DB_PASSWORD")
        url = System.getenv("DB_URL")
    }
    jdbcTemplate(JdbcTemplate, dataSource) {

    }
    userDao(UserDao, jdbcTemplate) {

    }
}