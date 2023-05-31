package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.UserController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/blockUser")
public class BlockUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId=Integer.parseInt(req.getParameter("userId"));
        UserController.blockUser(userId);
        resp.sendRedirect(req.getContextPath()+"/userControl");
    }
}
