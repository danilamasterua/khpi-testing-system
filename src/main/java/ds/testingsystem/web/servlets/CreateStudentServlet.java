package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.UserController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createStudent")
public class CreateStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int gId = Integer.parseInt(req.getParameter("groupId"));
        int testId = Integer.parseInt(req.getParameter("testId"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        int res = UserController.createUserStudent(firstName, lastName, email, gId);
        if (res==-1){
            throw new RuntimeException();
        }
    }
}
