package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.DatabaseUserRepository;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.impl.UserServiceImpl;
import org.geektimes.projects.user.sql.DBConnectionManager;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.sql.DriverManager;

@Path("/register")
public class UserController implements PageController {

    private UserService userService;

    public UserController() {
        DBConnectionManager dbConnectionManager = new DBConnectionManager();
        try {
            dbConnectionManager.setConnection(DriverManager.getConnection(DBConnectionManager.DATABASE_URL));
        } catch (Exception exception) {
            // 如果出错了，直接抛出运行时异常
            exception.printStackTrace();
            throw new RuntimeException(exception.getCause());
        }
        userService = new UserServiceImpl(new DatabaseUserRepository(dbConnectionManager));
    }

    @GET
    @POST
    @Path("")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "register.jsp";
    }

    @GET
    @POST
    @Path("/doRegister")
    public String doRegister(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        System.out.println(user);
        boolean register = userService.register(user);
        if (register) {
            return "success.jsp";
        }
        return "error.jsp";
    }


}
