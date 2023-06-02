package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Model;
import ds.testingsystem.web.controllers.TestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/getPassedTests")
public class GetPassedTestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        model.setPassedTests(TestController.getPassedTests(model.getCurrentUser()));
        req.setAttribute("model", model);
        session.setAttribute("model", model);
        getServletContext().getRequestDispatcher("/passedTests.jsp").forward(req, resp);
    }
}
