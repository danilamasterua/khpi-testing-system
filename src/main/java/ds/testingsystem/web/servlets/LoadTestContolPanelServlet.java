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

@WebServlet("/test-control-panel")
public class LoadTestContolPanelServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        try {
            req.setAttribute("tests", TestController.getTestsCreatedByUser(model.getCurrentUser().getUserId()));
            getServletContext().getRequestDispatcher("/testControlPanel.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
