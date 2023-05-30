package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.User;
import ds.testingsystem.web.controllers.UserController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId=Integer.parseInt(req.getParameter("userId"));
        User user = UserController.getUserById(userId);
        req.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/updateUser.jsp").forward(req,resp);
    }
}
