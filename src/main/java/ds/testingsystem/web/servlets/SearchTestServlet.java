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

@WebServlet("/searchTest")
public class SearchTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("search");
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        model.setTests(TestController.searchTest(model.getCurrentUser().getUserId(), name));
        req.setAttribute("model", model);
        req.setAttribute("teachers", UserController.getTeachers());
        session.setAttribute("model", model);
        getServletContext().getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }
}
