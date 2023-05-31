package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.UserController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getBlockedUsers")
public class GetBlockedUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", UserController.getBlockedUsers());
        getServletContext().getRequestDispatcher("/blockedUsers.jsp").forward(req, resp);
    }
}
