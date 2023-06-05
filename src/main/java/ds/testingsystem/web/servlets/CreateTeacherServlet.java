package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.UserController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createTeacher")
public class CreateTeacherServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        try {
            UserController.createUserTeacher(firstName, lastName, email);
            resp.sendRedirect(req.getContextPath()+"/userControl");
        } catch (Exception e){
            req.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getContextPath()+"/userControl");
        }
    }
}
