package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.TestController;
import ds.testingsystem.database.model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/getTests")
public class GetTestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        model.setPassedTests(TestController.getPassedTests(model.getCurrentUser()));
        req.setAttribute("model", model);
        session.setAttribute("model", model);
        getServletContext().getRequestDispatcher("/passedTests.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        try {
            model.setTests(TestController.getAvailableTests(model.getCurrentUser().getLogin()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("model", model);
        session.setAttribute("model", model);
        getServletContext().getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }
}
