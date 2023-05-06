package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Group;
import ds.testingsystem.database.model.Test;
import ds.testingsystem.web.controllers.TestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/loadTestAccessPage")
public class LoadTestAccessPanelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("testId"));
        try {
            Test test = TestController.loadTest(testId);
            HashMap<Integer, Group> acessedGroup = TestController.getAccessedGroup(testId);
            HashMap<Integer, Group> notAcessedGroup = TestController.getNotAccessedGroup(testId);
            req.setAttribute("test", test);
            req.setAttribute("accessedGroups", acessedGroup);
            req.setAttribute("notAcessedGroups", notAcessedGroup);
            req.setAttribute("testId", testId);
            getServletContext().getRequestDispatcher("/testAccess.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
