package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.User;
import ds.testingsystem.web.controllers.UserController;
import ds.testingsystem.database.model.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("pass");
        ServletContext servletContext = getServletContext();
        try {
            User user = UserController.getUserDataFromDB(login);
            if(user.getPassword()!=null&&user.getLogin()!=null) {
                if (UserController.checkUserPassword(login, password)) {
                    HttpSession session = req.getSession();
                    Model model = new Model();
                    model.setCurrentUser(user);
                    session.setAttribute("model", model);
                    resp.sendRedirect(req.getContextPath() + "/getTests");
                } else {
                    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/index.jsp");
                    req.setAttribute("error", "Пароль або логін неправильні");
                    requestDispatcher.forward(req, resp);
                }
            } else {
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/index.jsp");
                req.setAttribute("error", "Пароль або логін неправильні");
                requestDispatcher.forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
