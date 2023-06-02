package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Model;
import ds.testingsystem.web.controllers.TestController;
import ds.testingsystem.web.controllers.UserController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/filtrateTest")
public class FiltrateTestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tUserId=Integer.parseInt(req.getParameter("teacherId"));
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        model.setTests(TestController.filtrateTests(model.getCurrentUser().getUserId(), tUserId));
        req.setAttribute("model", model);
        req.setAttribute("teachers", UserController.getTeachers());
        session.setAttribute("model", model);
        getServletContext().getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }
}
