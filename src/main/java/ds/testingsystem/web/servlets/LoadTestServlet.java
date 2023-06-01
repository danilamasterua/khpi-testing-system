package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Model;
import ds.testingsystem.web.controllers.SupportController;
import ds.testingsystem.web.controllers.TestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/loadTest")
public class LoadTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("testId"));
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        try {
            req.setAttribute("test", TestController.loadTestForPass(testId, model.getCurrentUser().getUserId()));
            req.setAttribute("nowDate", LocalDateTime.now());
            req.setAttribute("testId", testId);
            req.setAttribute("accessData", SupportController.getAccessData(testId, model.getCurrentUser().getGroupId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/test.jsp").forward(req, resp);
    }
}
