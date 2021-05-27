package kr.ac.jejunu.user;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import java.io.IOException;

public class UserServlet extends GenericServlet {
    private UserDao userDao;

    @Override
    public void destroy() {
        System.out.println("*************** destroy *****************");
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("kr.ac.jejunu.user");
        userDao = applicationContext.getBean("userDao", UserDao.class);
        System.out.println("*************** init *****************");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        User user = userDao.findById(1);
        res.setContentType("text/html; charset=UTF-8");
        StringBuffer response = new StringBuffer();
        response.append("<html>");
        response.append("<body>");
        response.append("<h1>");
        response.append(String.format("Hello %s !!!", user.getName()));
        response.append("</h1>");
        response.append("</body>");
        response.append("</html>");
        res.getWriter().println(response.toString());

    }
}
