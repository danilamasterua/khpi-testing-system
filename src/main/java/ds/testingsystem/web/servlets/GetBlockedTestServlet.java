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
import java.sql.SQLException;

@WebServlet("/get-blocked-test")
public class GetBlockedTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        try {
            req.setAttribute("tests", TestController.getBlockedTest(model.getCurrentUser().getUserId()));
            getServletContext().getRequestDispatcher("/blockedTest.jsp").forward(req,resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
