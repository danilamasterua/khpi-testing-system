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
import java.sql.SQLException;

@WebServlet("/sortTests")
public class SortTestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean sort = Boolean.parseBoolean(req.getParameter("sortType"));
        System.out.println(sort);
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        try {
            model.setTests(TestController.getAvailableTests(model.getCurrentUser().getLogin(), sort));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("model", model);
        session.setAttribute("model", model);
        req.setAttribute("teachers", UserController.getTeachers());
        getServletContext().getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }
}
